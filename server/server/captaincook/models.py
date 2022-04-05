from django.contrib.auth.models import User
from django.db import models

# Create your models here.
class Ingredient(models.Model):
    ingrediet_name = models.CharField(max_length=150, default="")

class Recipe(models.Model):
    user_id=models.ForeignKey(User,default=0,on_delete=models.CASCADE)
    recipe_name=models.CharField(max_length=150,default="")
    recipe_preparation=models.CharField(max_length=500,default="")
    recipe_preparation_time=models.IntegerField(default=0)
    recipe_calories=models.IntegerField(default=0)
    recipe_image_url = models.CharField(max_length=500, default="https://good-food.ro/wp-content/uploads/2020/03/GoodFood-LOGO-300x283px.png")
    # recipe_image_url = models.ImageField()


class RecipeIngredients(models.Model):
    recipe_id=models.ForeignKey(Recipe,on_delete=models.CASCADE)
    ingredient_id=models.ForeignKey(Ingredient,on_delete=models.CASCADE)
    quantity=models.IntegerField(default=100)