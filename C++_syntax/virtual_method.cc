#include<iostream>
#include<vector>

using namespace std;

class Animal {
    public:
        void eat() const {
            cout<<"I eat like a generic Animal.\n";
        }
        ~Animal() {}
};

class Wolf: public Animal {
    public:
        void eat() const {
            cout<<"I eat like a wolf!\n";
        }
};

class Fish: public Animal {
    public:
        void eat() const {
            cout<<"I eat like a fish!\n";
        }
};

class GoldFish: public Fish {
    public:
        void eat() const {
            cout<<"I eat like a goldfish!\n";
        }
};

class OtherAnimal: public Animal {
};

int main() {
    vector<Animal*> animals;
    animals.push_back(new Animal());
    animals.push_back(new Wolf());
    animals.push_back(new Fish());
    animals.push_back(new GoldFish());
    animals.push_back(new OtherAnimal());

    for (vector<Animal*>::const_iterator it = animals.begin(); it != animals.end(); ++it) {
        (*it)->eat();
        delete *it;
    }

    return 0;
}

