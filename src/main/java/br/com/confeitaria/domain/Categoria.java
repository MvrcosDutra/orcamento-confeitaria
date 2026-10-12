package br.com.confeitaria.domain;

public enum Categoria {
    BOLO("Bolo"),
    DOCE("Doce"),
    SALGADO("Salgado"),
    ADICIONAL("Adicional");

    private final String descricao;

    Categoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
