package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleirojogo.Peca;
import tabuleirojogo.Posicao;
import tabuleirojogo.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private int turno;
	private Cor jogadorDaVez;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;

	private List<Peca> pecaNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorDaVez = Cor.BRANCO;
		iniciarPartida();
	}

	public Cor getJogadorDaVez() {
		return jogadorDaVez;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	public int getTurno() {
		return turno;
	}

	public PecaXadrez[][] getPecas() {
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int l = 0; l < tabuleiro.getLinhas(); l++) {
			for (int c = 0; c < tabuleiro.getColunas(); c++) {
				mat[l][c] = (PecaXadrez) tabuleiro.peca(l, c);
			}
		}
		return mat;
	}

	public boolean[][] movimentosPossiveis(PosicaoXadrez origemPosicao) {
		Posicao posicao = origemPosicao.paraPosicao();
		validarPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).possiveisMovimentos();
	}

	public PecaXadrez moverPecaXadrez(PosicaoXadrez origemPosicao, PosicaoXadrez destinoPosicao) {
		Posicao origem = origemPosicao.paraPosicao();
		Posicao destino = destinoPosicao.paraPosicao();
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		Peca pecaCapturada = fazerMover(origem, destino);

		if (testeCheck(jogadorDaVez)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new ExcecaoXadrez("Voce nao pode se colocar em check");
		}

		check = (testeCheck(oponente(jogadorDaVez))) ? true : false;
		
		if (testeCheck(oponente(jogadorDaVez))) {
			checkMate = true;
		}
		else {
			proximoTurno();
		}
		
		return (PecaXadrez) pecaCapturada;

	}

	private Peca fazerMover(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removePeca(origem);
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.colocarPeca(p, destino);

		if (pecaCapturada != null) {
			pecaNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		return pecaCapturada;
	}

	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		Peca p = tabuleiro.removePeca(destino);
		tabuleiro.colocarPeca(p, origem);

		if (pecaCapturada != null) {
			tabuleiro.colocarPeca(pecaCapturada, destino);
			pecasCapturadas.add(pecaCapturada);
			pecaNoTabuleiro.add(pecaCapturada);
		}
	}

	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.pecaAqui(posicao)) {
			throw new ExcecaoXadrez("Nao existe peca na posicao de origem");
		}
		if (jogadorDaVez != ((PecaXadrez) tabuleiro.peca(posicao)).getCor()) {
			throw new ExcecaoXadrez("A peca escolhida nao e sua");
		}
		if (!tabuleiro.peca(posicao).peloMenosUmMovimento()) {
			throw new ExcecaoXadrez("Nao existe movimentos possiveis para a peca escolhida");
		}

	}

	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).possivelMovimento(destino)) {
			throw new ExcecaoXadrez("A peca escolhida nao pode se mover para o destino");
		}
	}

	private void proximoTurno() {
		turno++;
		jogadorDaVez = (jogadorDaVez == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private PecaXadrez rei(Cor cor) {
		List<Peca> lista = pecaNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : lista) {
			if (p instanceof Rei) {
				return (PecaXadrez) p;
			}
		}
		throw new IllegalStateException("Nao possui Rei" + cor + "no tabuleiro");

	}

	private boolean testeCheck(Cor cor) {
		Posicao reiPosicao = rei(cor).getPosicaoXadrez().paraPosicao();
		List<Peca> oponentePecas = pecaNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == oponente(cor))
				.collect(Collectors.toList());
		for (Peca p : oponentePecas) {
			boolean[][] mat = p.possiveisMovimentos();
			if (mat[reiPosicao.getLinha()][reiPosicao.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testeCheckMate(Cor cor) {
		if (!testeCheck(cor)) {
			return false;
		}
		List<Peca> lista = pecaNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : lista) {
			boolean[][] mat = p.possiveisMovimentos();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((PecaXadrez)p).getPosicaoXadrez().paraPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = fazerMover(origem, destino);
						boolean testCheck = testeCheck(cor);
						desfazerMovimento(origem, destino, pecaCapturada);
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void colocarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
		pecaNoTabuleiro.add(peca);
	}

	private void iniciarPartida() {
		colocarNovaPeca('h', 7, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('d', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO));

		colocarNovaPeca('b', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('a', 8, new Rei(tabuleiro, Cor.PRETO));

	}
}
