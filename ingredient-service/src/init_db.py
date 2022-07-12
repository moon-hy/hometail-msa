import csv
import os

import django


os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'config.settings')
django.setup()


from ingredient.models import Ingredient, Representation, Category


with open('ingredient.csv') as csvfile:
    reader = csv.reader(csvfile, delimiter=',',)
    next(reader, None)
    categories = set()
    representations = set()
    ingredients = set()

    for row in reader:
        category, representation, ingredient, description, abv = row
        if category not in categories and not Category.objects.filter(name=category).exists():
            categories.add(category)
        if (category, representation) not in representations and not Representation.objects.filter(name=representation).exists():
            representations.add((category, representation))
        if (representation, ingredient) not in ingredients and not Ingredient.objects.filter(name=ingredient).exists():
            ingredients.add((category, representation, ingredient, description, abv))

    category_objects = []
    for category in categories:
        category_objects.append(Category(name=category))
    Category.objects.bulk_create(category_objects)

    representation_objects = []
    for category, representation in representations:
        representation_objects.append(Representation(
            category=Category.objects.get(name=category), 
            name=representation))
    Representation.objects.bulk_create(representation_objects)

    ingredient_objects = []
    for _, representation, ingredient, description, abv in ingredients:
        ingredient_objects.append(Ingredient(
            representation=Representation.objects.get(name=representation),
            name=ingredient,
            description=description,
            alcohol_by_volume=abv,
            created_by=1))
    Ingredient.objects.bulk_create(ingredient_objects)
