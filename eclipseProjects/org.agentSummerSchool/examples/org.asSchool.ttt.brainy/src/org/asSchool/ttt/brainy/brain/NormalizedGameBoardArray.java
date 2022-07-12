package org.asSchool.ttt.brainy.brain;

import org.asSchool.ttt.dataModel.GameWrapper;
import org.asSchool.ttt.dataModel.ontology.AbstractMarkType;
import org.asSchool.ttt.dataModel.ontology.Game;
import org.asSchool.ttt.dataModel.ontology.GameMove;

/**
 * The Class NormalizedGameBoardArray.
 *
 * @author Christian Derksen - SOFTEC - ICB - University of Duisburg-Essen
 */
public class NormalizedGameBoardArray {

	public enum Transformations {
		Rotate90DegressPlus,
		Rotate90DegressMinus,
		Rotate180Degress;
	}
	
	private AbstractMarkType[][] originalGameBoardArray;
	private AbstractMarkType[][] normalizedGameBoardArray;

	private Transformations normalizeTransformation;
	private Transformations inverseTransformation;
	
	
	/**
	 * Instantiates a new normalized game board array.
	 * @param game the game
	 */
	public NormalizedGameBoardArray(Game game) {
		this.setGameBoardArrays(game);
	}
	
	/**
	 * Sets the local game board arrays, while the transformation to be used are stored.
	 * @param game the new game board arrays
	 */
	public void setGameBoardArrays(Game game) {
		
		// --- Remind the original game board array -----------------
		AbstractMarkType[][] markArray = GameWrapper.transformToMarkArray(game.getGameBoard());
		this.setOriginalGameBoardArray(markArray);
		
		// --- Determine the movement to normalize the array --------
		GameMove gm = (GameMove) game.getGameMoveHistory().get(0);
		int firstRow = gm.getGameRow();
		int firstCol = gm.getGameColumn();

		switch (firstRow) {
		case 1:
			// --- First row ----------
			switch (firstCol) {
			case 1:
				this.normalizeTransformation = null;
				this.inverseTransformation = null;
				break;

			case 2:
				this.normalizeTransformation = null;
				this.inverseTransformation = null;
				break;
				
			case 3:
				this.normalizeTransformation = Transformations.Rotate90DegressMinus;
				this.inverseTransformation = Transformations.Rotate90DegressPlus;
				break;
			}
			break;
		
		case 2:
			// --- Second row ---------
			switch (firstCol) {
			case 1:
				this.normalizeTransformation = Transformations.Rotate90DegressPlus;
				this.inverseTransformation = Transformations.Rotate90DegressMinus;
				break;

			case 2:
				this.normalizeTransformation = null;
				this.inverseTransformation = null;
				break;
				
			case 3:
				this.normalizeTransformation = Transformations.Rotate90DegressMinus;
				this.inverseTransformation = Transformations.Rotate90DegressPlus;
				break;
			}
			break;
		
		case 3:
			// --- Third row ----------
			switch (firstCol) {
			case 1:
				this.normalizeTransformation = Transformations.Rotate90DegressPlus;
				this.inverseTransformation = Transformations.Rotate90DegressMinus;
				break;

			case 2:
				this.normalizeTransformation = Transformations.Rotate180Degress;
				this.inverseTransformation = Transformations.Rotate180Degress;
				break;
				
			case 3:
				this.normalizeTransformation = Transformations.Rotate180Degress;
				this.inverseTransformation = Transformations.Rotate180Degress;
				break;
			}
			break;
		}
		
		// --- Normalize according to the normalize transformation --
		this.setNormalizedGameBoardArray(this.transformMarkArray(markArray, this.normalizeTransformation));
	}

	/**
	 * Transforms the specified mark array.
	 *
	 * @param markArray the mark array
	 * @param transformation the transformation
	 * @return the abstract mark type[][]
	 */
	private AbstractMarkType[][] transformMarkArray(AbstractMarkType[][] markArray, Transformations transformation) {
		
		if (transformation==null) return GameWrapper.copyMarkArray(markArray);
		
		AbstractMarkType[][] markArrayTransformed = null;
		// --- Do the normalization transformation ------------------
		switch (transformation) {
		case Rotate90DegressPlus:
			markArrayTransformed = GameWrapper.rotateMarkArrayBy90DegreesPlus(markArray);
			break;

		case Rotate90DegressMinus:
			markArrayTransformed = GameWrapper.rotateMarkArrayBy90DegreesMinus(markArray);
			break;
			
		case Rotate180Degress:
			markArrayTransformed = GameWrapper.rotateMarkArrayBy180Degrees(markArray);
			break;
		}
		return markArrayTransformed;
	}
	
	
	/**
	 * Returns the original game board array.
	 * @return the original game board array
	 */
	public AbstractMarkType[][] getOriginalGameBoardArray() {
		return originalGameBoardArray;
	}
	/**
	 * Sets the original game board array.
	 * @param originalGameBoardArray the new original game board array
	 */
	public void setOriginalGameBoardArray(AbstractMarkType[][] originalGameBoardArray) {
		this.originalGameBoardArray = originalGameBoardArray;
	}
	
	
	/**
	 * Returns the normalized game board array.
	 * @return the normalized game board array
	 */
	public AbstractMarkType[][] getNormalizedGameBoardArray() {
		return normalizedGameBoardArray;
	}
	/**
	 * Sets the normalized game board array.
	 * @param normalizedGameBoardArray the new normalized game board array
	 */
	public void setNormalizedGameBoardArray(AbstractMarkType[][] normalizedGameBoardArray) {
		this.normalizedGameBoardArray = normalizedGameBoardArray;
	}
	
	
	/**
	 * Return the inverse game move for the specified position in the normalized game board array.
	 *
	 * @param normRow the normalized row index
	 * @param normCol the normalized column index
	 * @return the inverse game move
	 */
	public GameMove getInversGameMove(int normRow, int normCol, AbstractMarkType markType) {
		
		// --- Set the mark to an empty matrix ------------
		AbstractMarkType[][] markArray = new AbstractMarkType[3][3];
		markArray[normRow][normCol] = markType;
		
		// --- Do the inverse transformation --------------
		markArray = this.transformMarkArray(markArray, this.inverseTransformation);
		
		// --- Find the position of the mark --------------
		int row = 0;
		int col = 0;
		outerLoop:
		for (row = 0; row < markArray.length; row++) {
			for (col = 0; col < markArray[row].length; col++) {
				if (markArray[row][col]!=null) break outerLoop;
			}
		}
		
		// --- Define the GameMove for original board -----
		GameMove igm = new GameMove();
		igm.setGameRow(row+1);
		igm.setGameColumn(col+1);
		igm.setMarkType(markType);
		return igm;
	}
	
}
