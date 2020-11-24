package com.pagaya.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pagaya.enums.CellColorEnum;

@Service
public class GameServiceImpl implements GameService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameServiceImpl.class);

	private static final int GRID_SIZE = 18;

	private static final int MAX_ATTEMPTS = 21;

	private final List<List<CellColorEnum>> grid;

	private final Scanner scanner;

	public GameServiceImpl() {

		grid = new ArrayList<List<CellColorEnum>>();
		scanner = new Scanner(System.in);
	}

	@PreDestroy
	private void preDestroy() {

		grid.clear();

		LOGGER.info("close scanner");

		scanner.close();
	}

	@Override
	public void startGame() {

		init();
		play();
	}

	private void play() {

		boolean isWin = false;

		printGrid();

		for (int i = 0 ; i < MAX_ATTEMPTS ; i++) {

			CellColorEnum selectedColorByUser = getSelectedColorByUser(i + 1);

			applyColorChange(selectedColorByUser);

			printGrid();

			isWin = isWin();

			if (isWin) {

				break;
			}
		}

		if (isWin) {

			System.out.println("Game over! You won!");

		} else {

			System.out.println("Game over! You lost!");
		}
	}

	private boolean isWin() {

		CellColorEnum firstCellColor = grid.get(0).get(0);

		for (int i = 0 ; i < GRID_SIZE ; i++) {

			List<CellColorEnum> row = grid.get(i);

			for (int j = 0 ; j < GRID_SIZE ; j++) {

				if (row.get(j) != firstCellColor) {

					return false;
				}
			}
		}

		return true;
	}

	private void applyColorChange(CellColorEnum selectedColorByUser) {

		CellColorEnum firstCellColor = grid.get(0).get(0);

		changeColor(0, 0, firstCellColor, selectedColorByUser);
	}

	private void changeColor(int i, int j, CellColorEnum firstCellColor, CellColorEnum newColor) {

		if (i >= 0 && i < GRID_SIZE && j >=0 && j < GRID_SIZE) {

			List<CellColorEnum> row = grid.get(i);

			if (row.get(j) == firstCellColor) {

				row.set(j, newColor);

				changeColor(i-1, j, firstCellColor, newColor); // top neighbor
				changeColor(i, j+1, firstCellColor, newColor); // right neighbor
				changeColor(i+1, j, firstCellColor, newColor); // lower neighbor
				changeColor(i, j-1, firstCellColor, newColor); // left neighbor
			}
		}
	}

	private CellColorEnum getSelectedColorByUser(int attempt) {

		CellColorEnum selectedColorByUser = null;

		String input;
		while (selectedColorByUser == null) {

			System.out.print("Attempt #" + attempt + ", Enter a color (r b g y): ");

			input = scanner.nextLine();

			selectedColorByUser = CellColorEnum.fromSymbol(input);
		}

		return selectedColorByUser;
	}

	private void init() {

		for (int i = 0 ; i < GRID_SIZE ; i++) {

			List<CellColorEnum> row = new ArrayList<>();

			for (int j = 0 ; j < GRID_SIZE ; j++) {

				row.add(getRandomCellColorEnum());
			}

			grid.add(row);
		}
	}

	private CellColorEnum getRandomCellColorEnum() {
		
		CellColorEnum[] cellColorEnumArr = CellColorEnum.values();

		int min = 0;
		int max = cellColorEnumArr.length - 1;
		
		int randomIndex = (int)(Math.random() * ((max - min) + 1)) + min;

		return cellColorEnumArr[randomIndex];
	}

	private void printGrid() {

		for (int i = 0 ; i < GRID_SIZE ; i++) {

			List<CellColorEnum> row = grid.get(i);

			for (int j = 0 ; j < GRID_SIZE ; j++) {

				System.out.print(row.get(j).getColor());
			}

			System.out.println();
		}
	}
}
