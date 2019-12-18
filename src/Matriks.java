/**
 *@author		:Panawar Hasibuan
 *@email		:panawarhsb28@gmail.com
 *@file 		:Matriks.java
 *@date			:27 September 2019
 */


public class Matriks
{
	//konstanta
	public static final int MAX = 100;

	/*ATRIBUTE*/
	private float augm[][];		//matriks augmented
	private int baris;	
	private int kolom;

	protected boolean setBrs = false;
	protected boolean setKlm = false;

	/*METHOD*/

	//--Konstruktor--
	public Matriks() {
		baris = 0;
		kolom = 0;
	}
	public Matriks(int brs, int kol) {
		augm = new float[MAX][MAX];
		baris = brs;
		kolom = kol;
		setBrs = setKlm = true;
	}
	/**
	 * Mengembalikan matriks identitas I
	 * 
	 * @param n ukuran matriks I
	 * 
	 * @return Matriks identitas berukuran nxn
	 */
	public Matriks(int n) {
		augm = new float[MAX][MAX];
		baris = n;
		kolom = n;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) {
					augm[i][j] = 1;
				} else {
					augm[i][j] = 0;
				}
			}
		}
		setBrs = setKlm = true;
	}

	/**
	 * Jumlah baris matriks.
	 * 
	 * @return banyak baris matriks.
	 */
	public int getBaris() {
		return this.baris;
	}
	/**
	 * 
	 * @return banyak kolom matriks.
	 */
	public int getKolom() {
		return this.kolom;
	}
	/**
	 * Mengembalikan array multi-dimensi augm Matriks.
	 * 
	 * @return this->augm
	 */
	public float[][] getAugm() {
		float[][] newAugm = new float[getBaris()][getKolom()];
		for (int i = 0; i < getBaris(); i++) {
			for (int j = 0; j < getKolom(); j++) {
				newAugm[i][j] = getElmt(i, j);
			}
		}
		return newAugm;
	}
	/**
	 * Mengembalikan elemen matriks pada posisi tertentu.
	 * @param brs posisi baris.
	 * @param kol posisi kolom.
	 * @return elemen pada indeks (brs,kol), 0 jika (brs,kol) tidak valid untuk matriks.
	 */
	public float getElmt(int brs, int kol) {
		if (isIndeksValid(brs,kol)) {
			return this.augm[brs][kol];
		} else {
			return 0;
		}
	}

	//--Setter
	/**
	 * Inisiasi baris matriks yang dibuat dengan konstruktor tanpa parameter.
	 * Hanya bisa dipanggil sekali.
	 * 
	 * @param brs
	 */
	public void setBaris(int brs) {
		if (!setBrs) {
			setBrs = true;
			this.baris = brs;
			if (setKlm) {
				this.augm = new float[this.baris][this.kolom];
			}
		}
	}
	/**
	 * Inisiasi kol matriks yang dibuat dengan konstruktor tanpa parameter.
	 * Hanya bisa dipanggil sekali.
	 * 
	 * @param kol
	 */
	public void setKolom(int kol) {
		if (!setKlm) {
			this.kolom = kol;
			this.setKlm = true;
			if (setBrs) {
				this.augm = new float[this.baris][this.kolom];
			}
		}
	}
	/**
	 * Mengisi aug dengan array multi-dimensi.
	 * Ukuran array multi-dimensi harus sesuai ukuran matriks.
	 * 
	 * @param newAugm
	 * @param brs ukuran baris array multi-dimensi.
	 * @param kol ukuran kolom array multi-dimensi.
	 */
	public void setAugm(float newAugm[][],int brs, int kol) {
		if (brs == getBaris() && kol == getKolom()) {
			for (int i = 0; i < brs; i++) {
				for (int j = 0; j < kol; j++) {
					setElmt(i, j, newAugm[i][j]);
				}
			}
		}
	}
	public void setElmt(int brs, int kol, float el) {
		if (isIndeksValid(brs,kol)) {
			this.augm[brs][kol] = el;	
		}
	}


	//--Method Primitif
	/**
	 * cek apakah matriks kosong
	 * 
	 * @return boolean
	 */
	public boolean isEmpty() {
		return (getBaris() == 0 && getKolom() == 0);
	}
	/**
	 * cek apakah indeks(brs,kol) valid untuk Matriks this
	 * 
	 * @param brs indeks baris
	 * @param kol indeks kolom
	 * @return boolean
	 */
	public boolean isIndeksValid(int brs, int kol) {
		return (brs < baris) && (kol < kolom);
	}
	public boolean isSquere() {
		return (getBaris() == getKolom());
	}
	/**
	 * Menyalin Matriks.
	 * 
	 * @return hasil salinan Matriks
	 */
	public Matriks copyMatriks() {
		Matriks M = new Matriks(getBaris(),getKolom());
		M.setAugm(getAugm(), getBaris(), getKolom());
		return M;
	}
	/**
	 * Penjumlahan antar Matriks.
	 * 
	 * @param M
	 * @return Matriks hasil penjumlahan antar matriks.
	 * Mengembalikan null jika ukuran matriks tidak sesuai.
	 */
	public Matriks tambahMatriks(Matriks M) {
		if (getBaris() == M.getBaris() && getKolom() == M.getKolom()) {
			Matriks M1 = new Matriks(getBaris(), getKolom());
			for(int i = 0; i < getBaris(); i++) {
				for (int j = 0; j < getKolom(); j++) {
					M1.setElmt(i,j,getElmt(i, j)+M.getElmt(i, j));
				}
			}
			return M1;
		} else {
			return null;
		}
	}
	/**
	 * Pengurangan antar matriks.
	 * 
	 * @param M
	 * @return Matriks hasil pengurangan antar matriks.
	 * Mengembalikan null jika ukuran matriks tidak sesuai.
	 */
	public Matriks kurangMatriks(Matriks M) {
		if (getBaris() == M.getBaris() && getKolom() == M.getKolom()) {
			Matriks M1 = new Matriks(getBaris(), getKolom());
			for(int i = 0; i < getBaris(); i++) {
				for (int j = 0; j < getKolom(); j++) {
					M1.setElmt(i,j,getElmt(i, j)-M.getElmt(i, j));
				}
			}
			return M1;
		} else {
			return null;
		}
	}
	/**
	 * Perkalian antar matriks.
	 * 
	 * @param M
	 * @return hasil perkalian Matriks dengan M.
	 * jika ukuran tidak sesuai, mengembalikan null.
	 */
	public Matriks kaliMatriks(Matriks M) {
		if (getBaris() == M.getKolom()) {
			Matriks M1;
			M1 = new Matriks(getBaris(),M.getKolom());
			for (int i = 0; i < getBaris(); i++) {
				for (int j = 0; j < M.getKolom(); j++) {
					float total = 0; 
					for (int k = 0; k < getKolom(); k++) {
						total = total +  (getElmt(i,k)*M.getElmt(k,j));
					}
					M1.setElmt(i,j,total);
				}       
			}
			return M1;
		} else {
			return null;
		}
	}
	/**
	 * Perkalian matriks dengan skalar.
	 */
	public void kaliSkalar(float skalar) {
		for (int i = 0; i < getBaris(); i++) {
			for (int j = 0; j < getKolom(); j++) {
				setElmt(i,j,getElmt(i,j)*skalar);
			}
		}
	}
}
