package com.pagaya.enums;

import com.pagaya.util.ConsoleColors;

public enum CellColorEnum {

	RED("r", ConsoleColors.RED + "\u25A0" + ConsoleColors.RESET),
	BLUE("b", ConsoleColors.BLUE + "\u25A0" + ConsoleColors.RESET),
	GREEN("g", ConsoleColors.GREEN + "\u25A0" + ConsoleColors.RESET),
	YELLOW("y", ConsoleColors.YELLOW + "\u25A0" + ConsoleColors.RESET);

	private String symbol;
	private String color;

	CellColorEnum(String symbol, String color) {
		this.symbol = symbol;
		this.color = color;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public String getColor() {
		return color;
	}
	
	public static CellColorEnum fromSymbol(String symbol) {
		
        for (CellColorEnum cellColorEnum : CellColorEnum.values()) {
        	
            if (cellColorEnum.symbol.equalsIgnoreCase(symbol)) {
                
            	return cellColorEnum;
            }
        }
        
        return null;
    }
}
