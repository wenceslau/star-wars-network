package com.letscode.starwars.model;

public class Enuns {

    public enum Gender {
        MASCULINO("Masculino"), FEMININO("Feminino"), INDEFINIDO("Indefinido");
        private String value;

        private Gender(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum ResourceType {
        ARMA("Arma", 4),  MUNICAO("Munição",3 ), COMIDA("Comida", 2), AGUA("Água", 1);
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

    public enum TypeExchange {
        OFFER, REQUEST;
    }

}
