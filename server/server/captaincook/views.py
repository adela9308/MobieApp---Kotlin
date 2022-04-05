from django.contrib.auth.models import User
from django.shortcuts import render
from django.http import HttpResponse, JsonResponse, QueryDict
from rest_framework import status
from rest_framework.decorators import api_view

from captaincook.models import Recipe, RecipeIngredients, Ingredient
from captaincook.serializers import RecipeSerializerGet, RecipeSerializerUpload, RecipeIngredientsSerializer, \
    IngredientSerializer
import time

def index(request):
    return HttpResponse("Hello, world. You're at the polls index.")
# Create your views here.


@api_view(['GET'])
def get_feed_recipes(request):
    if request.method == 'GET':
        recipes = Recipe.objects.all()
        recipe_serializer = RecipeSerializerGet(recipes,many=True)
        print("Get all the recipes called")
        time.sleep(2)
        return JsonResponse(recipe_serializer.data,safe=False)

@api_view(['GET'])
def get_my_recipes(request):
    if request.method == 'GET':
        recipes = Recipe.objects.filter(user_id=1)
        recipe_serializer = RecipeSerializerGet(recipes,many=True)
        print("Get my recipes called")
        return JsonResponse(recipe_serializer.data,safe=False)

@api_view(['GET'])
def get_all_ingredients(request):
    if request.method == 'GET':
        ingredients = Ingredient.objects.all()
        ingredient_serializer = IngredientSerializer(ingredients, many=True)
        print("Get all ingredients called")
        return JsonResponse(ingredient_serializer.data, safe=False)

@api_view(['GET'])
def get_all_recipe_ingredients(request):
    if request.method == 'GET':
        recipe_ingredients = RecipeIngredients.objects.all()
        recipe_ingredient_serializer = RecipeIngredientsSerializer(recipe_ingredients, many=True)
        print("Get all recipe ingredients called")
        return JsonResponse(recipe_ingredient_serializer.data, safe=False)

@api_view(['GET'])
def get_ingredients_for_recipe(request,recipeID):
    if request.method == 'GET':

        recipe_ingredients = RecipeIngredients.objects.filter(recipe_id=recipeID)
        list=[]
        for el in recipe_ingredients:
            list.append(el.ingredient_id)
        ingredient_serializer = IngredientSerializer(list, many=True)
        print("Get ingredients for recipe with ID " + recipeID)
        return JsonResponse(ingredient_serializer.data, safe=False)


@api_view(['POST'])
def add_recipe(request):
    try:
        recipe_data = request.data
        recipe_data['user_id'] = 1
        recipeSerializer = RecipeSerializerUpload(data=request.data)
        if recipeSerializer.is_valid():
            recipe = recipeSerializer.save()
            print("Recipe "+recipe.recipe_name+" added")
            newRecipe = RecipeSerializerGet(Recipe.objects.get(pk=recipe.id))
            return JsonResponse(newRecipe.data, status=status.HTTP_200_OK)
        else:
            print("Error while adding recipe")
            return JsonResponse({'message': recipeSerializer.errors}, status=status.HTTP_400_BAD_REQUEST)
    except Exception as e:
        print("Error while adding recipe")
        return JsonResponse({'message': e}, status=status.HTTP_400_BAD_REQUEST)



@api_view(['GET','DELETE','PUT'])
def get_delete_update_recipe(request,recipeID):
    if request.method == 'GET':
        # get_recipe_by_id(request,recipeID)
        try:
            recipe = Recipe.objects.get(pk=recipeID)
        except Recipe.DoesNotExist:
            print("Error while reading recipe with ID: " + str(recipeID))
            return JsonResponse({'message': 'The recipe does not exist'}, status=status.HTTP_404_NOT_FOUND)
        if request.method == 'GET':
            print("Recipe with ID: " + str(recipeID) + " read.")
            recipe_serializer = RecipeSerializerGet(recipe)
            return JsonResponse(recipe_serializer.data)

    if request.method == 'DELETE':
        # delete_recipe(request,recipeID)
        try:
            entity = Recipe.objects.get(id=recipeID)
            entity.delete()
            print("Recipe with ID: "+str(recipeID)+" deleted")
            return JsonResponse({'message': 'The recipe were deleted! '}, safe=False, status=status.HTTP_200_OK)
        except Exception as e:
            print("Error while deleting recipe with ID: "+str(recipeID))
            return JsonResponse([], safe=False, status=status.HTTP_200_OK)

    if request.method == 'PUT':
        # update_recipe(request,recipeID)
        data = request.data
        recipe = Recipe.objects.get(id=recipeID)
        recipe.recipe_name = data['recipe_name']
        recipe.recipe_preparation = data['recipe_preparation']
        recipe.recipe_preparation_time = data['recipe_preparation_time']
        recipe.recipe_calories = data['recipe_calories']
        upload_recipe_serializer = RecipeSerializerUpload(recipe, request.FILES)
        if upload_recipe_serializer.is_valid():
            upload_recipe_serializer.save()
            print("Updating recipe with ID: "+str(recipeID))
            return JsonResponse(upload_recipe_serializer.data, safe=False, status=status.HTTP_201_CREATED)
        else:
            print("Error while updating recipe with ID: "+str(recipeID))
            return JsonResponse({'message': 'The recipe was not updated! '}, status=status.HTTP_404_NOT_FOUND)


@api_view(['DELETE'])
def delete_recipe_ingredients_from_recipe(request, recipeID):
    if (request.method == "DELETE"):
        try:
            list = RecipeIngredients.objects.filter(recipe_id=recipeID)
            print(len(list))
            list=reversed(list)

            for entity in list:
                entity.delete()
            print("Deleted recipe_ingredients for recipe with ID: "+str(recipeID))
            return JsonResponse({'message': 'The recipe ingredients were deleted! '}, safe=False, status=status.HTTP_200_OK)
        except Exception as e:
            print("Error while deleting recipe_ingredients for recipe with ID: "+str(recipeID))
            return JsonResponse([], safe=False, status=status.HTTP_200_OK)


@api_view(['POST'])
def add_recipe_ingredients(request):
    recipeIngredientsSerializer = RecipeIngredientsSerializer(data=request.data)
    if recipeIngredientsSerializer.is_valid():
        entity = recipeIngredientsSerializer.save()
        newEntity = RecipeIngredientsSerializer(RecipeIngredients.objects.get(pk=entity.id))
        print("Adding recipe_ingredient with ingredient_id= "+str(entity.ingredient_id)+" and recipeID= "+str(entity.recipe_id))
        return JsonResponse(newEntity.data, status=status.HTTP_200_OK)
    else:
        return JsonResponse({'message': recipeIngredientsSerializer.errors}, status=status.HTTP_400_BAD_REQUEST)


# @api_view(['DELETE'])
def delete_recipe(request, recipeID):
    # if (request.method == "DELETE"):
        try:
            entity = Recipe.objects.get(id=recipeID)
            entity.delete()
            return JsonResponse(entity, safe=False, status=status.HTTP_200_OK)
        except Exception as e:
            return JsonResponse([], safe=False, status=status.HTTP_200_OK)


# @api_view(['PUT'])
def update_recipe(request,recipeID):
    # if request.method == "PUT":
        data = request.data
        recipe = Recipe.objects.get(id = recipeID)
        recipe.recipe_name = data['recipe_name']
        recipe.recipe_preparation = data['recipe_preparation']
        recipe.recipe_preparation_time = data['recipe_preparation_time']
        recipe.recipe_calories = data['recipe_calories']
        upload_recipe_serializer = RecipeSerializerUpload(recipe, request.FILES)
        if upload_recipe_serializer.is_valid():
            upload_recipe_serializer.save()
            return JsonResponse(upload_recipe_serializer.data, safe=False, status=status.HTTP_201_CREATED)
        else:
            return JsonResponse({'message': 'The recipe was not updated! '}, status=status.HTTP_404_NOT_FOUND)

# @api_view(['GET'])
def get_recipe_by_id(request,recipeID):
    try:
        recipe = Recipe.objects.get(pk=recipeID)
    except Recipe.DoesNotExist:
        print("Error while reading recipe with ID: " + recipeID)
        return JsonResponse({'message': 'The recipe does not exist'}, status=status.HTTP_404_NOT_FOUND)
    if request.method == 'GET':
        print("Recipe with ID: "+recipeID+" read.")
        recipe_serializer = RecipeSerializerGet(recipe)
        return JsonResponse(recipe_serializer.data)
