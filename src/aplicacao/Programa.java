package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.ExcecaoXadrez;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class Programa {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner leia = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		List <PecaXadrez> capturada = new ArrayList<>();

		while (true) {
			try {
				UI.limparTela();
				UI.printPartida(partidaXadrez, capturada);
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
				
				if (pecaCapturada != null) {
					capturada.add(pecaCapturada);
				}
				
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
