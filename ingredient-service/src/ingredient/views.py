from rest_framework import views, status
from rest_framework.response import Response

from ingredient.models import Ingredient
from ingredient.serializers import IngredientSerializer


class Ingredients(views.APIView):
    serializer_class = IngredientSerializer

    def get(self, request):
        ingredients = Ingredient.objects.all()
        serializer = self.serializer_class(ingredients, many=True)
        return Response(data=serializer.data, status=status.HTTP_200_OK)

    def post(self, request):
        pass

    def delete(self, request):
        pass

    def put(self, request):
        pass

    def patch(self, request):
        pass
