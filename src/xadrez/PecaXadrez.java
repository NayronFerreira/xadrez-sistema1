package xadrez;

import tabuleirojogo.Peca;
import tabuleirojogo.Posicao;
import tabuleirojogo.Tabuleiro;

public abstract class PecaXadrez extends Peca{
	
	private Cor cor;
	private int contagemMovimento;

	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public int getContagemMovimento() {
		return contagemMovimento;
	}
	public void incrementarContagemMovimento() {
		contagemMovimento++;
	}
	
	public void decrementarContagemMovimento() {
		contagemMovimento--;
	}
	
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.dePosicao(posicao);
	}
	
	protected boolean aquiTemPecaOponente (Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p!= null && p.getCor()!=cor;
	
	}
}
