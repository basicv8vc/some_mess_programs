#-*-coding:utf-8 -*-

class Animal:

    def eat(self):
        print("I eat like a generic animal.")

class Fish(Animal):

    def eat(self):
        print("I eat like a fish.")

class Wolf(Animal):

    def eat(self):
        print("I eat like a wolf.")

class GoldFish(Fish):

    def eat(self):
        print("I eat like a goldfish.")

class OtherAnimal(Animal):
    pass

if __name__ == "__main__":
    animal = Animal()
    animal.eat()
    fish = Fish()
    fish.eat()
    wolf = Wolf()
    wolf.eat()
    gfish = GoldFish()
    gfish.eat()
    other_animal = OtherAnimal()
    other_animal.eat()
