package obj;

public class Medicamento extends Produto{
	private String principio_ativo;

	public Medicamento() {
		super();
	}

	public Medicamento(String nome, double preco, String validade, int quantidade, String codigoDeBarras) {
		super(nome, preco, validade, quantidade, codigoDeBarras);
	}
	
	public String getPrincipio_ativo() {
		return principio_ativo;
	}

	public void setPrincipio_ativo(String principio_ativo) {
		this.principio_ativo = principio_ativo;
	}

}