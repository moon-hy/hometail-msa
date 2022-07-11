from django.db import models
from django.utils.translation import gettext_lazy as _


class Ingredient(models.Model):
    '''
    # 재료 테이블
    [FK] 대표
    이름
    설명
    알코올도수
    (구매방법)
    '''
    respresentation     = models.ForeignKey(
        to              ="Representation", 
        related_name    ="ingredients",
        verbose_name    =_("Representation"), 
        on_delete       =models.CASCADE)

    name                = models.CharField(
        verbose_name    =_("Ingredient Name"), 
        max_length      =50)

    description         = models.TextField(
        verbose_name    =_("Description"))

    alcohol_by_volume   = models.DecimalField(
        verbose_name    =_("ABV"), 
        max_digits      =5, 
        decimal_places  =2)

class Representation(models.Model):
    '''
    # 대표 테이블
    [FK] 카테고리
    대표이름
    '''
    category            = models.ForeignKey(
        to              ="Category", 
        verbose_name    =_("Category"), 
        on_delete       =models.CASCADE)

    name                = models.CharField(
        verbose_name    =_("Representation Name"), 
        max_length      =50)
    pass

class Category(models.Model):
    '''
    # 카테고리 테이블
    카테고리이름
    '''
    name                = models.CharField(
        verbose_name    =_("Category Name"), 
        max_length      =50)