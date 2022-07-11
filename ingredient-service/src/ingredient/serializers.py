from rest_framework import serializers
from rest_framework.response import Response

from ingredient.models import Category, Ingredient, Representation


class IngredientSerializer(serializers.ModelSerializer):

    class Meta:
        model = Ingredient


class RepresentationSerializer(serializers.ModelSerializer):

    class Meta:
        model = Representation


class CategorySerializer(serializers.ModelSerializer):

    class Meta:
        model = Category
