package com.letscode.starwars.model;

public class Enuns {

    public enum Gender {
        MALE("Masculino"), FEMALE("Feminino"), UNDEFINED("Indefinido");
        private String value;

        private Gender(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum ResourceType {
        GUN("Arma", 4),  MUNITION("Munição",3 ), FOOD("Comida", 2), WATER("Água", 1);
        private String name;
        private Integer credit;

        ResourceType(String name, Integer credit) {
            this.name = name;
            this.credit = credit;
        }

        public String getName() {
            return name;
        }

        public Integer getCredit() {
            return credit;
        }
    }
}
