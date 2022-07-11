from rest_framework import views, status
from rest_framework.response import Response

from ingredient.models import Ingredient
from ingredient.serializers import IngredientSerializer, IngredientListSerializer


HEADER_AUTHORIZATION_ID = 'X-Authorization-Id'
HEADER_AUTHORIZATION_ROLE = 'X-Authorization-Role'


def parse_authorization_id(request):
    return request.headers.get(HEADER_AUTHORIZATION_ID, None)


def parse_authorization_role(request):
    return request.headers.get(HEADER_AUTHORIZATION_ROLE, None)


class IngredientsView(views.APIView):
    serializer_class = IngredientListSerializer

    def get(self, request):
        ingredients = Ingredient.objects.all()
        serializer = self.serializer_class(ingredients, many=True)

        return Response(data={
            'ingredients': serializer.data
        }, status=status.HTTP_200_OK)

    def post(self, request):
        account_id = parse_authorization_id(request)
        serializer = self.serializer_class(data=request.data)
        if serializer.is_valid():
            serializer.save(created_by=account_id)
            return Response(status=status.HTTP_201_CREATED)
        return Response(data=serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class IngredientView(views.APIView):
    serializer_class = IngredientSerializer

    def get(self, request):
        pass

    def put(self, request):
        pass

    def patch(self, request):
        pass

    def delete(self, request):
        pass
