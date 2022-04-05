# Generated by Django 4.0 on 2021-12-29 18:40

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        ('auth', '0012_alter_user_first_name_max_length'),
    ]

    operations = [
        migrations.CreateModel(
            name='Ingredient',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('ingrediet_name', models.CharField(default='', max_length=150)),
            ],
        ),
        migrations.CreateModel(
            name='RecipeIngredients',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('quantity', models.IntegerField(default=100)),
                ('ingredient_id', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='captaincook.ingredient')),
            ],
        ),
        migrations.CreateModel(
            name='Recipe',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('recipe_name', models.CharField(default='', max_length=150)),
                ('recipe_preparation', models.CharField(default='', max_length=500)),
                ('recipe_preparation_time', models.IntegerField(default=0)),
                ('recipe_calories', models.IntegerField(default=0)),
                ('recipe_image_url', models.ImageField(upload_to='')),
                ('user_id', models.ForeignKey(default=0, on_delete=django.db.models.deletion.CASCADE, to='auth.user')),
            ],
        ),
    ]
