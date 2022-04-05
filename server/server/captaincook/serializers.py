from django import forms
from rest_framework import serializers
from django.contrib.auth.models import User
from captaincook.models import *

class RecipeSerializerGet(serializers.ModelSerializer):
    class Meta:
        model = Recipe
        fields = (
            "id",
            "user_id",
            "recipe_name",
            "recipe_preparation",
            "recipe_preparation_time",
            "recipe_calories",
            "recipe_image_url"
        )

class RecipeSerializerUpload(serializers.ModelSerializer):
    class Meta:
        model = Recipe
        fields = (
            "id",
            "user_id",
            "recipe_name",
            "recipe_preparation",
            "recipe_preparation_time",
            "recipe_calories",
            "recipe_image_url"
        )

class RecipeIngredientsSerializer(serializers.ModelSerializer):
    class Meta:
        model = RecipeIngredients
        fields = (
            "recipe_id",
            "ingredient_id",
            "quantity"
        )

class IngredientSerializer(serializers.ModelSerializer):
    class Meta:
        model = Ingredient
        fields = (
            "id",
            "ingrediet_name"
        )