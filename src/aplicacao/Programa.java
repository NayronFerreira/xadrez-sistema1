package aplicacao;

import java.util.InputMismatchException;
import java.util.Scanner;

import tabuleirojogo.ExcecaoTabuleiro;
import xadrez.ExcecaoXadrez;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class Programa {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner leia = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();

		while (true) {
			try {
				UI.limparTela();
				UI.printTabuleiro(partidaXadrez.getPecas());
				System.out.println();
				System.out.print("Origem: ");
				PosicaoXadrez origem = UI.lerPosicaoXadrez(leia);
				
				boolean [][] possiveisMovimentos = partidaXadrez.movimentosPossiveis(origem);
				UI.limparTela();
				UI.printTabuleiro(partidaXadrez.getPecas(), possiveisMovimentos);

				System.out.println();
				System.out.print("Destino: ");
				PosicaoXadrez destino = UI.lerPosicaoXadrez(leia);

				PecaXadrez pecaCapturada = partidaXadrez.moverPecaXadrez(origem, destino);
			} catch (ExcecaoXadrez x) {
				System.out.println(x.getMessage());
				leia.nextLine();
			} catch (InputMismatchException x) {
				System.out.println(x.getMessage());
				leia.nextLine();
			}
		}

	}

}
