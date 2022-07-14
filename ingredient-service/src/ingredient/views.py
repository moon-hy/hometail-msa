from rest_framework import views, status
from rest_framework.response import Response
from rest_framework.pagination import PageNumberPagination

from ingredient.models import Category, Ingredient
from ingredient.pagination import PaginationHandlerMixin
from ingredient.serializers import CategorySerializer, IngredientSerializer, IngredientListSerializer


HEADER_AUTHORIZATION_ID = 'X-Authorization-Id'
HEADER_AUTHORIZATION_ROLE = 'X-Authorization-Role'


def parse_authorization_id(request):
    return request.headers.get(HEADER_AUTHORIZATION_ID, None)


def parse_authorization_role(request):
    return request.headers.get(HEADER_AUTHORIZATION_ROLE, None)


class IngredientsView(views.APIView, PaginationHandlerMixin):
    serializer_class = IngredientListSerializer

    def get(self, request):
        ingredients = Ingredient.objects.all()

        if category:=request.query_params.get('category'):
            ingredients = ingredients.filter(representation__category=category)

        serializer = self.serializer_class(ingredients, many=True)

        return Response(data={
            'count': len(serializer.data),
            'ingredients': serializer.data
        }, status=status.HTTP_200_OK)

    def post(self, request):
        '''
        # 재료 등록
        request:
        {
            "name": "INGREDIENT_NAME",
            "representation": REPRESENTATION_ID,
            "description": "INGREDIENT_DESCRIPTION",
            "alcohol_by_volume": INGREDIENT_ABV
        }
        '''
        account_id = parse_authorization_id(request)
        serializer = self.serializer_class(data=request.data)
        if serializer.is_valid():
            serializer.save(created_by=account_id)
            return Response(data=serializer.data, status=status.HTTP_201_CREATED)
        return Response(data=serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class IngredientView(views.APIView):
    serializer_class = IngredientSerializer

    def get(self, request, pk):
        ingredient = Ingredient.objects.get(pk=pk)
        serializer = self.serializer_class(ingredient)
        return Response(data=serializer.data, status=status.HTTP_200_OK)

    def patch(self, request, pk):
        ingredient = Ingredient.objects.get(pk=pk)
        serializer = self.serializer_class(ingredient, data=request.data, partial=True)
        if serializer.is_valid():
            serializer.save()
            return Response(data=serializer.data, status=status.HTTP_200_OK)
        return Response(data=serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, pk):
        ingredient = Ingredient.objects.get(pk=pk)
        ingredient.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)

class CategoryView(views.APIView):
    serializer_class = CategorySerializer

    def get_category_links(self, categories):
        links = {}
        for category in categories:
            links[category.name] = f'/ingredient-service/ingredients?category={category.id}'
        return links

    def get(self, request):
        categories = Category.objects.all()
        serializer = self.serializer_class(categories, many=True)
        links = self.get_category_links(categories)
        return Response(data={
            'count': len(serializer.data),
            'categories': serializer.data,
            '_links': links
        }, status=status.HTTP_200_OK)
