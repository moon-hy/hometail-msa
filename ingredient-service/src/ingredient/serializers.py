from rest_framework import serializers

from ingredient.models import Category, Ingredient, Representation


class IngredientSerializer(serializers.ModelSerializer):
    created_by = serializers.IntegerField(required=False)

    class Meta:
        model = Ingredient
        fields = '__all__'

    def to_representation(self, instance):
        data = super().to_representation(instance)
        data['representation'] = instance.representation.name
        return data


class IngredientListSerializer(IngredientSerializer):

    class Meta:
        model = Ingredient
        fields = '__all__'
        excluded_fields = [
            'id', 'description', 'created_by', 'alcohol_by_volume']

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
