from django.contrib import admin

from captaincook.models import *

admin.site.register(Recipe)
admin.site.register(Ingredient)
admin.site.register(RecipeIngredients)


# Register your models here.
