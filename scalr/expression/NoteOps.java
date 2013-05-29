
package scalr.expression;

import scalr.Degree;
import scalr.Length;
import scalr.variable.Note;
import scalr.variable.Scale;
import scalr.variable.ScalrNum;

/**
 * This class handles all modifications to {@linkplain Note}s. Since a {@linkplain Note} cannot be necessarily
 * modified at the time that it is parsed. It is rather complex in its evaluation,
 * but it simply decodes what the parser parsed and executes the appropriate modification. Long chains of note
 * modifications (note.length().pitch()...) are represented as long chains of {@linkplain NoteOps} {@linkplain
 * Expression}s.
 */
public class NoteOps implements Expression
{
	private Expression	       note;
	private String	           type;
	private Expression	num;
	private String	   mod;
	private Degree	   deg;
	private Length	   len;
	private Expression	index;
	private Expression	scale;

	/**
	 * Constructs a {@linkplain NoteOps} that changes the given parameter of the {@linkplain Note}. Since the type of
	 * modification is the first thing that is known, {@linkplain NoteOps} are constructed using this central piece
	 * of information.
	 * @param type A {@linkplain String} corresponding to the type of modification to the {@linkplain Note}. It can
	 *                be "pit" (pitch), "vol" (volume), or "len" (length).
	 */
	public NoteOps(String type)
	{
		this.type = type;
	}

	/**
	 * Adds the note to perform this modification to. The grammar automatically chains a series of {@linkplain
	 * NoteOps} starting with a {@linkplain Note} or {@linkplain VariableReference},
	 * however any expression of <code>{@linkplain ExpressionType}.NOTE</code> will suffice.
	 * @param expr The {@linkplain Expression} to operate on, it should be of <code>{@linkplain ExpressionType}
	 *                .NOTE</code>.
	 */
	public void addOperand(Expression expr)
	{
		note = expr;
	}

	/**
	 * If the parameter of the {@linkplain Note} is being modded, then this method is used to set the direction of the
	 * mod.
	 * @param s A {@linkplain String} indicating that the parameter of the {@linkplain Note} is to be modded,
	 *             and not set. If it is "+", then the value is to be increased. If it is "-",
	 *             then the value is to be decreased.
	 */
	public void addMod(String s)
	{
		mod = s;
	}

	/**
	 * Adds the number to mod the {@linkplain Note} by or set the {@linkplain Note}'s parameter to. The {@linkplain
	 * Expression} should be of <code>{@link ExpressionType}.NUMBER</code>.
	 * @param expr An {@linkplain Expression} of <code>{@link scalr.expression.ExpressionType}.NUMBER</code>.
	 */
	public void addNum(Expression expr)
	{
		num = expr;
	}

	/**
	 * Indicates to this {@linkplain NoteOps} that the pitch is being set absolutely. To avoid nasty regular
	 * expressions, pitch and length have their own methods that the grammar calls appropriately.
	 * @param pit A {@linkplain String} containing corresponding to the name of a {@linkplain Degree} enum.
	 */
	public void addPitch(String pit)
	{
		deg = Degree.valueOf(pit);
	}

	/**
	 * Serves a similar purpose as addPitch. Indicates to this {@linkplain NoteOps} that the {@linkplain Length} is
	 * to be set absolutely. This method handles both fractions (1, 1/2, etc.) as well as the {@linkplain Length}
	 * names.
	 * @param len A {@linkplain String} indicating the value of {@linkplain Length} to set.
	 */
	public void addLength(String len)
	{
		for (Length l : Length.values()) {
			if (l.duration.equals(len)) {
				this.len = l;
				return;
			}
		}
		// If we didn't return, then we could have the actual length
		this.len = Length.valueOf(len);
	}

	/**
	 * Adds the index of the scale to access. The {@linkplain Expression} should be of type <code>{@link scalr
	 * .expression.ExpressionType}.NUMBER</code>.
	 * @param expr An {@linkplain Expression} of type <code>{@link scalr.expression.ExpressionType}.NUMBER</code>,
	 *                indicating the position in the {@linkplain Scale}, starting from 1,
	 *                to access the {@linkplain Degree}.
	 */
	public void addIndex(Expression expr)
	{
		index = expr;
	}

	/**
	 * Adds a {@linkplain Scale} to this {@linkplain NoteOps}, indicating that the pitch modification will be a
	 * {@linkplain Degree} taken from a {@linkplain Scale}.
	 * @param expr An {@linkplain Expression} of <code>{@link scalr.expression.ExpressionType}.SCALE</code>. The
	 *                <code>addIndex()</code> method should be called before evaluation.
	 */
	public void addScale(Expression expr)
	{
		scale = expr;
	}

	/**
	 * Evaluates this {@linkplain NoteOps}. This method is essentially a ton of if statements to determine which
	 * fields were set, and thus perform the appropriate operation. The grammar produces valid and deterministic
	 * cases, but it may be tough to unit test. Regardless, if the syntax is valid,
	 * this method always returns an {@linkplain Expression} of type {@linkplain Note}.
	 *
	 * @return An {@linkplain Expression} of type {@linkplain Note} with the indicated modification. Multiple
	 * {@linkplain NoteOps} may be chained together to return the final {@linkplain Note}.
	 */
	@Override
	public Expression getValue()
	{
		if (note.getType() == ExpressionType.NOTE) {
			Note n = (Note) note.getValue();
			if (type.equals("pit")) {
				if (mod != null) {
					ScalrNum sn = (ScalrNum) num.getValue();
					if (mod.equals("+"))
						n.modPitch(sn.getNum());
					else if (mod.equals("-"))
						n.modPitch(-sn.getNum());
					return n;
				}
				else if (num != null) {
					ScalrNum sn = (ScalrNum) num.getValue();
					System.out.println("sn = " + sn);
					return n.setPitch(Degree.values()[sn.getNum()]);
				}
				else if (scale != null) {
					Scale s = (Scale) scale.getValue();
					ScalrNum sn = (ScalrNum) index.getValue();
					return n.setPitch(s.getDegree(sn.getNum()));
				}
				else
					return n.setPitch(deg);
			}
			else if (type.equals("len")) {
				if (len != null)
					return n.setLength(len);
				else {
					ScalrNum sn = (ScalrNum) num.getValue();
					if (mod == null)
						n.setLength(Length.values()[sn.getNum()]);
					else if (mod.equals("+"))
						n.modLength(sn.getNum());
					else if (mod.equals("-"))
						n.modLength(-sn.getNum());
					return n;
				}
			}
			else if (type.equals("vol")) {
				if (num.getType() != ExpressionType.NUMBER)
					return null;

				ScalrNum sn = (ScalrNum) num.getValue();
				if (mod != null) {
					if (mod.equals("+"))
						n.modVolume(sn.getNum());
					else if (mod.equals("-"))
						n.modVolume(-sn.getNum());
				}
				else
					n.setVolume(sn.getNum());
				return n;
			}
		}
		return null;
	}

	/**
	 * It modifies a {@linkplain Note}, and thus always returns a {@linkplain Note}.
	 * @return Returns <code>{@link scalr.expression.ExpressionType}.NOTE</code> if the operand is also of
	 * <code>{@link scalr.expression.ExpressionType}.NOTE</code>. <code>null</code> otherwise.
	 */
	@Override
	public ExpressionType getType()
	{
		if (note.getType() == ExpressionType.NOTE)
			return ExpressionType.NOTE;
		return null;
	}

	/**
	 * Decodes the fields to determine the modification that the user performed. This method is safe so long as the
	 * {@linkplain Note} operand and/or {@linkplain Scale} operand have safe <code>toString()</code> methods.
	 *
	 * @return A {@linkplain String} displaying the {@linkplain Note} operation as the SCALR programmer may have
	 * written it.
	 */
	@Override
	public String toString()
	{
		if (type.equals("pit")) {
			if (mod != null)
				return note + ".pitch(" + mod + num + ")";
			else if (scale != null)
				return note + ".pitch(" + scale + "[" + index + "])";
			else
				return note + ".pitch(" + deg + ")";
		}
		else if (type.equals("vol")) {
			if (mod != null)
				return note + ".volume(" + mod + num + ")";
			else
				return note + ".volume(" + num + ")";
		}
		else {
			if (len != null)
				return note + ".length(" + len + ")";
			else
				return note + ".length(" + mod + num + ")";
		}
	}
}
