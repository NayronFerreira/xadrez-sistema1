package xadrez.pecas;

import tabuleirojogo.Posicao;
import tabuleirojogo.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Rainha extends PecaXadrez {

	public Rainha(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "R";
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// noroeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().pecaAqui(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() - 1, p.getColuna() - 1);
		}
		if (getTabuleiro().posicaoExiste(p) && aquiTemPecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// nordeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().pecaAqui(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() - 1, p.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExiste(p) && aquiTemPecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// sudoeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().pecaAqui(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() + 1, p.getColuna() - 1);
		}
		if (getTabuleiro().posicaoExiste(p) && aquiTemPecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// sudeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().pecaAqui(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() + 1, p.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExiste(p) && aquiTemPecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// para cima
				p.setValores(posicao.getLinha() - 1, posicao.getColuna());
				while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().pecaAqui(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setLinha(p.getLinha() - 1);
				}
				if (getTabuleiro().posicaoExiste(p) && aquiTemPecaOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}

				// esquerda
				p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
				while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().pecaAqui(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setColuna(p.getColuna() - 1);
				}
				if (getTabuleiro().posicaoExiste(p) && aquiTemPecaOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}

				// direita
				p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
				while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().pecaAqui(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setColuna(p.getColuna() + 1);
				}
				if (getTabuleiro().posicaoExiste(p) && aquiTemPecaOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}

				// para baixo
				p.setValores(posicao.getLinha() + 1, posicao.getColuna());
				while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().pecaAqui(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setLinha(p.getLinha() + 1);
				}
				if (getTabuleiro().posicaoExiste(p) && aquiTemPecaOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
		return mat;
	}

}
