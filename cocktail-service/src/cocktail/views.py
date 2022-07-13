from django.http import Http404
from rest_framework import views
from rest_framework.response import Response
from rest_framework import status
from cocktail.models import Cocktail

from cocktail.serializers import CocktailListSerializer, CocktailSeriralizer


'''
Gateway를 통해서 오는 request는 
X-Authorization-Id, X-Authorization-Role Header를 가지고 있음.
'''
HEADER_AUTHORIZATION_ID = 'X-Authorization-Id'
HEADER_AUTHORIZATION_ROLE = 'X-Authorization-Role'


def parse_authorization_id(request):
    return request.headers.get(HEADER_AUTHORIZATION_ID, None)


def parse_authorization_role(request):
    return request.headers.get(HEADER_AUTHORIZATION_ROLE, None)


class CocktailsAPI(views.APIView):
    serializer_class = CocktailListSerializer

    def get(self, request):
        cocktails = Cocktail.objects.all()
        serializer = self.serializer_class(cocktails, many=True)

        return Response(data={
            'cocktails': serializer.data
        }, status=status.HTTP_200_OK)

    def post(self, request):
        '''
        # 칵테일 등록
        request:
        {
            "name": "COCKTAIL_NAME",
            "base": "COCKTAIL_BASE",
            "color": "COLOR_NAME",
            "tags": ["TAG1", "TAG2"],
            "recipe": [
                {
                    "ingredient": INGREDIENT_ID,
                    "alcohol_by_volume": INGREDIENT_ABV // ingredient-service 정보, Client에서 받아옴
                    "volume": INGREDIENT_VOLUME,
                    "unit": "INGREDIENT_UNIT",
                    "optional": false
                }
            ]
        }
        '''
        account_id = parse_authorization_id(request)
        serializer = self.serializer_class(data=request.data)
        if serializer.is_valid():
            serializer.save(created_by=account_id)
            return Response(data=serializer.data, status=status.HTTP_201_CREATED)
        return Response(data=serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class CocktailAPI(views.APIView):
    serializer_class = CocktailSeriralizer

    def get_object(self, pk):
        try:
            return Cocktail.objects.get(pk=pk)
        except:
            raise Http404

    def get(self, request, pk):
        cocktail = self.get_object(pk)
        serializer = self.serializer_class(cocktail)
        return Response(data=serializer.data, status=status.HTTP_200_OK)

    def delete(self, request, pk):
        cocktail = self.get_object(pk)
        cocktail.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)
