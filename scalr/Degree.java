
package scalr;

import scalr.variable.Note;

/**
 * The collection of valid notes for each {@linkplain Note}s. All valid MIDI notes are specified here (starting from
 * 0, not -5) along with their actual midi value. In addition, a special {@linkplain Degree}, br,
 * represents a musical break. Its associated MIDI value of 200 means that MIDI won't actually produce a sound when
 * it is played.
 */
public enum Degree {
	C0(0), Cs0(1), Db0(1), D0(2), Ds0(3), Eb0(3), E0(4), F0(5), Fs0(6), Gb0(6), G0(7), Gs0(8), Ab0(
	        8), A0(9), As0(10), Bb0(10), B0(11), C1(12), Cs1(13), Db1(13), D1(14), Ds1(15),
	Eb1(15), E1(16), F1(17), Fs1(18), Gb1(18), G1(19), Gs1(20), Ab1(20), A1(21), As1(22), Bb1(22),
	B1(23), C2(24), Cs2(25), Db2(25), D2(26), Ds2(27), Eb2(27), E2(28), F2(29), Fs2(30), Gb2(30),
	G2(31), Gs2(32), Ab2(32), A2(33), As2(34), Bb2(34), B2(35), C3(36), Cs3(37), Db3(37), D3(38),
	Ds3(39), Eb3(39), E3(40), F3(41), Fs3(42), Gb3(42), G3(43), Gs3(44), Ab3(44), A3(45), As3(46),
	Bb3(46), B3(47), C4(48), Cs4(49), Db4(49), D4(50), Ds4(51), Eb4(51), E4(52), F4(53), Fs4(54),
	Gb4(54), G4(55), Gs4(56), Ab4(56), A4(57), As4(58), Bb4(58), B4(59), C5(60), Cs5(61), Db5(61),
	D5(62), Ds5(63), Eb5(63), E5(64), F5(65), Fs5(66), Gb5(66), G5(67), Gs5(68), Ab5(68), A5(69),
	As5(70), Bb5(70), B5(71), C6(72), Cs6(73), Db6(73), D6(74), Ds6(75), Eb6(75), E6(76), F6(77),
	Fs6(78), Gb6(78), G6(79), Gs6(80), Ab6(80), A6(81), As6(82), Bb6(82), B6(83), C7(84), Cs7(85),
	Db7(85), D7(86), Ds7(87), Eb7(87), E7(88), F7(89), Fs7(90), Gb7(90), G7(91), Gs7(92), Ab7(92),
	A7(93), As7(94), Bb7(94), B7(95), C8(96), Cs8(97), Db8(97), D8(98), Ds8(99), Eb8(99), E8(100),
	F8(101), Fs8(102), Gb8(102), G8(103), Gs8(104), Ab8(104), A8(105), As8(106), Bb8(106), B8(107),
	C9(108), Cs9(109), Db9(109), D9(110), Ds9(111), Eb9(111), E9(112), F9(113), Fs9(114), Gb9(114),
	G9(115), Gs9(116), Ab9(116), A9(117), As9(118), Bb9(118), B9(119), C10(120), Cs10(121), Db10(
	        121), D10(122), Ds10(123), Eb10(123), E10(124), F10(125), Fs10(126), Gb10(126),
	G10(127), br(200);
	private final int	value;

	private Degree(int value)
	{
		this.value = value;
	}

	/**
	 * Returns the MIDI pitch that this {@linkplain Degree} represents. Note that this number and the ordinal are not
	 * necessarily the same, and is not the same for any {@linkplain Degree} higher than <code>Cs0</code>.
	 *
	 * @return An <code>int</code> corresponding to the MIDI pitch of this {@linkplain Degree}. Guaranteed to be a
	 * Natural Number.
	 */
	public int getMidi()
	{
		return value;
	}
}
