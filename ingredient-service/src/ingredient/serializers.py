from rest_framework import serializers

from ingredient.models import Category, Ingredient, Representation


class IngredientSerializer(serializers.ModelSerializer):

    class Meta:
        model = Ingredient
        fields = [
            'id', 'name', 'description', 'alcohol_by_volume', 
            'created_by', 'representation'
        ]

    def to_representation(self, instance):
        data = super().to_representation(instance)
        data['representation'] = instance.representation.name
        data['_links'] = {
            'detail': f'/ingredient-service/ingredients/{instance.id}'
        }
        return data


class IngredientListSerializer(IngredientSerializer):

    class Meta:
        model = Ingredient
        fields = [
            'id', 'name', 'description', 'alcohol_by_volume', 
            'created_by', 'representation'
        ]
        excluded_fields = [
            'description', 'created_by']

    def to_representation(self, instance):
        data = super().to_representation(instance)
        for field in self.Meta.excluded_fields:
            data.pop(field)
        return data


class RepresentationSerializer(serializers.ModelSerializer):

    class Meta:
        model = Representation
        fields = '__all__'


class CategorySerializer(serializers.ModelSerializer):

    class Meta:
        model = Category
        fields = '__all__'
