from django.db import models
from django.utils.translation import gettext_lazy as _

# Create your models here.
class Cocktail(models.Model):
    '''
    # 칵테일 테이블
    이름
    설명
    도수
    베이스
    [M2M]재료 리스트
    제조 방법
    색상
    [M2M]태그
    '''
    name = models.CharField(
        verbose_name=_("Cocktail Name"), 
        max_length=50)

    description = models.TextField(
        verbose_name=_("Cocktail Description"),
        blank=True)
        
    alcohol_by_volume = models.DecimalField(
        verbose_name=_("Cocktail ABV"), 
        max_digits=5, 
        decimal_places=2,
        blank=True)
    
    base = models.CharField(
        verbose_name=_("Cocktail Base"),
        max_length=16)

    detail = models.TextField(
        verbose_name=_("Cocktail Detail"),
        blank=True)
    
    color = models.CharField(
        verbose_name=_("Cocktail Color"),
        max_length=16)

    tags = models.ManyToManyField(
        to="Tag", 
        verbose_name=_("Cocktail Tag"),
        blank=True)

    created_by = models.IntegerField(
        verbose_name=_("Created User Id"))

    def __str__(self):
        return self.name


class Recipe(models.Model):
    '''
    # 칵테일 재료 중간 테이블
    [FK] 칵테일
    [~FK] 재료
    용량
    단위
    '''
    cocktail = models.ForeignKey(
        to="Cocktail",
        related_name="recipe",
        verbose_name=_("Cocktail"),
        blank=True,
        on_delete=models.CASCADE)

    ingredient = models.IntegerField(
        verbose_name=_("Ingredient"))

    volume = models.IntegerField(
        verbose_name=_("Ingredient Volume"))

    unit = models.CharField(
        verbose_name=_("Ingredient Unit"),
        max_length=16)

    optional = models.BooleanField(
        verbose_name=_("Optional"),
        default=False)
    
    def __str__(self):
        return f'{self.cocktail.id} | {self.ingredient} | {str(self.volume)} {self.unit}'


class Tag(models.Model):
    '''
    # 태그 테이블
    이름
    '''
    name = models.CharField(
        verbose_name=_("Tag Name"), 
        max_length=50,
        primary_key=True)

    def __str__(self):
        return self.name