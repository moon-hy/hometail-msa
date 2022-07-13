from django.urls import path

from cocktail.views import CocktailAPI, CocktailsAPI


urlpatterns = [
    path('/cocktails', CocktailsAPI.as_view()),
    path('/cocktails/<int:pk>', CocktailAPI.as_view())
]
