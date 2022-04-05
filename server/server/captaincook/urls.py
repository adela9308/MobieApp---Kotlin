from django.conf import settings
from django.conf.urls.static import static
from django.urls import path

from . import views

urlpatterns = [
    path('', views.index, name='index'),
    path('api/recipes/<int:recipeID>', views.get_delete_update_recipe),

    # get
    path('api/recipes/all', views.get_feed_recipes),
    path('api/recipes/my_recipes', views.get_my_recipes),
    path('api/recipe_ingredients/recipe/<int:recipeID>', views.get_ingredients_for_recipe),
    path('api/recipe_ingredients/all', views.get_all_recipe_ingredients),
    path('api/ingredients/all', views.get_all_ingredients),

    # add
    path('api/recipes', views.add_recipe),
    path('api/recipe_ingredients', views.add_recipe_ingredients),

    # delete
    path('api/recipe_ingredients/<int:recipeID>', views.delete_recipe_ingredients_from_recipe),

]

