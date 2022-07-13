from django.contrib import admin

from cocktail.models import Cocktail, Recipe, Tag


admin.site.register(Cocktail)
admin.site.register(Recipe)
admin.site.register(Tag)