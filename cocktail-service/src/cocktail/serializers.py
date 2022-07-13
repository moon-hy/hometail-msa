from rest_framework import serializers
from django.db import transaction

from cocktail.models import Cocktail, Recipe


class RecipeSerializer(serializers.ModelSerializer):
    alcohol_by_volume = serializers.FloatField(write_only=True)

    class Meta:
        model = Recipe
        fields = [
            'id', 'ingredient', 'volume', 'unit', 'optional', 'alcohol_by_volume']


class CocktailSeriralizer(serializers.ModelSerializer):
    recipe = RecipeSerializer(many=True)

    class Meta:
        model = Cocktail
        fields = [
            'id', 'name', 'base', 'alcohol_by_volume', 'recipe', 'description', 
            'color', 'detail', 'created_by', 'tags']

    def create(self, validated_data):
        with transaction.atomic():
            ingredients = validated_data.pop('recipe', [])
            tags = validated_data.pop('tags', [])

            cocktail = Cocktail.objects.create(**validated_data)
            total_volume = 0.0001
            total_alcohol = 0

            for ingredient in ingredients:
                total_volume += ingredient.get('volume', 0)
                total_alcohol += ingredient.get('volume', 0) * ingredient.pop('alcohol_by_volume', 0)
                Recipe.objects.create(cocktail=cocktail, **ingredient)

            cocktail.alcohol_by_volume = round(total_alcohol/total_volume, 1)
            cocktail.save()
            return cocktail

class CocktailListSerializer(CocktailSeriralizer):
    
    class Meta:
        model = Cocktail
        fields = [
            'id', 'name', 'base', 'alcohol_by_volume', 'recipe', 'description', 
            'color', 'detail', 'created_by', 'tags']
        excluded_fields = [
            'recipe', 'description', 'ingredients', 'detail', 'created_by'
        ]

    
    def to_representation(self, instance):
        data = super().to_representation(instance)
        for field in self.Meta.excluded_fields:
            if field in data:
                data.pop(field)
        return data
