/**
 *@author		:Panawar Hasibuan
 *@email		:panawarhsb28@gmail.com
 *@file 		:Obe.java
 *@date		    :08 September 2019
 */


public class Obe extends Matriks
{
    /*ATRIBUTE*/
    int pivotBaris[];
    int pivotKolom[];
    float faktorDeterminan;

    /*METHOD*/
    
    public Obe() {
        super();
        faktorDeterminan = 1;
    }
    public Obe(int brs, int kol) {
        super(brs, kol);
        pivotBaris = new int[brs];
        pivotKolom = new int[kol];
        faktorDeterminan = 1;
		for (int i = 0; i < brs; i++) {
			pivotBaris[i] = 0;
        }
        for (int i = 0; i < kol; i++) {
			pivotKolom[i] = 0;
		}
    }
    public Obe(int n) {
        super(n);
        pivotBaris = new int[n];
        pivotKolom = new int[n];
        faktorDeterminan = 1;
		for (int i = 0; i < n; i++) {
			pivotBaris[i] = i;
			pivotKolom[i] = i;
		}
	}
	
	@Override
	public void setBaris(int brs) {
		// TODO Auto-generated method stub
		super.setBaris(brs);
		if (!setBrs) {
			if (setKlm) {
				pivotBaris = new int[getBaris()];
				pivotKolom = new int[getKolom()];
			}
		}
	}
	@Override
	public void setKolom(int kol) {
		// TODO Auto-generated method stub
		super.setKolom(kol);
		if (!setKlm) {
			if (setBrs) {
				pivotBaris = new int[getBaris()];
				pivotKolom = new int[getKolom()];
			}
		}
	}

	/**
	 * Melakukan operasi baris perkalian sekalar.
	 * 
	 * @param brs baris yang dikalikan dengan skalar.
	 * @param skalar skalar yang dikalikan.
	 */
	public void kaliBaris(int brs, float skalar) {
		faktorDeterminan *= (1/skalar);
		for (int j = 0; j < getKolom(); j++) {
			setElmt(brs,j,getElmt(brs,j)*skalar);
		}
	}
	/**
	 * Menukar posisi dua baris.
	 * 
	 * @param brs1 baris pertama yang ditukar.
	 * @param brs2 baris kedua yang ditukar.
	 */
	public void swapBaris(int brs1, int brs2) {
		faktorDeterminan *= (-1);
		float[] temp = new float[this.getKolom()];
		for (int j = 0; j < getKolom(); j++) {
			temp[j] = this.getElmt(brs1,j);
		}

		for (int j = 0; j < getKolom(); j++) {
			setElmt(brs1,j,getElmt(brs2,j));
		}
		for (int j = 0; j < getKolom(); j++) {
			setElmt(brs2,j,temp[j]);
		}
	}
	/**
	 * Menjumlahkan dua baris berbeda, R1 = R1 + R2*skalar.
	 * 
	 * @param brs1 baris tujuan.
	 * @param brs2 baris yang dijumlahkan.
	 * @param skalar skalar pengali baris  yang dijumlahkan.
	 */
	public void tambahBaris(int brs1, int brs2, float skalar) {
		float[] temp = new float[this.getKolom()];
		for (int j = 0; j < getKolom(); j++) {
			temp[j] = this.getElmt(brs2,j)*skalar;
		}
		for (int j = 0; j < getKolom(); j++) {
			setElmt(brs1,j,temp[j]+getElmt(brs1,j));
		}
	}
	/**
	 * Kondisional matriks echelon form.
	 * @return True jika matriks merupakan matriks echelon form.
	 */
	public boolean isEF() {
		return faktorDeterminan != 1;
	}
	/**
	 * Membuat matriks kedalam Echelon Form.
	 */
	public void makeEF() {
		//kamus
		int i,k,pivot;
		//algoritma
		pivot = 0;
		i = 0;
		while (pivot < getKolom() && i < getBaris()-1) {
			//swap baris jika elemen pertama 0
			if (getElmt(i,pivot) == 0) {
				k = i+1;
					while (k < getBaris() && getElmt(k,pivot) == 0) {
						k++;
					}
					if (getElmt(k,pivot) == 0) {	//semua kolom pivot dari baris i sampai baris akhir bernilai 0
						pivot++;
					} else {
						swapBaris(i,k);
					}
			}
			//membuat nol semua bilangan dibawah pivot
			if (pivot < getKolom() && getElmt(i,pivot) != 0) {	//cek apakah baris sudah di swap
				kaliBaris(i,1/getElmt(i,pivot));	//leading 1 (pivot)
				//posisi leading 1 (pivot)
				pivotBaris[i] = pivot;
				pivotKolom[pivot] = i;
				for (k = i+1; k < getBaris(); k++) {
					if (getElmt(k,pivot) != 0) {
						tambahBaris(k,i,getElmt(k,pivot)*(-1));
					}
				}
				i++;	//baris berikutnya, pivot di increase
				pivot++;
			}
		}	//pivot >= getKolom() || i == getBaris()-1
		if (pivot < getKolom()) {	//i == getBaris()-1, last row
			while (pivot < getKolom() && getElmt(i,pivot) == 0) {
				pivot++;
			}
			if (pivot < getKolom()) {
				kaliBaris(i,1/getElmt(i,pivot));	//leading 1 (pivot)
				//posisi leading 1 (pivot)
				pivotBaris[i] = pivot;
				pivotKolom[pivot] = i;
			}
		}
	}
	/**
	 * Membuat Matriks menjadi Echelon Form tereduksi.
	 */
	public void makeEFR() {
		//kamus
		int i,k,pivot;
		//algoritma
		if (!isEF()) {
			makeEF();
		}
		i = getBaris()-1;
		while (i > 0) {	//traversal dari bawah
			pivot = pivotBaris[i];
			if (pivot > 0 && pivot < getKolom()) {	//baris memiliki leading one (pivot)
				k = 0;
				while (k < i) {
					tambahBaris(k,i,getElmt(k,pivot)*(-1));
					k++;
				}	//k == i
				i--; //next baris
			} else {
				i--;
			}
		}	// i == 0
	}
    /**
	 * Lokasi pivot/leading poin matriks.
	 * 
	 * @param brs true untuk pivot baris, false untuk pivot kolom
	 * @return array pivot
	 */
	public int[] getPivot(boolean brs) {
		int[] pivot;
		if (brs) {
			pivot = new int[getBaris()];
			for (int i = 0; i < getBaris(); i++) {
				pivot[i] = pivotBaris[i];
			}
		} else {
			pivot = new int[getKolom()];
			for (int j = 0; j < getKolom(); j++) {
				pivot[j] = pivotKolom[j];
			}
		}
		return pivot;
	}
	/**
	 * Mengambil vektor matriks dari kolom tertentu.
	 * @param j kolom yang diambil.
	 * @return kolom j matriks.
	 * mengembalikan null jika j tidak valid.
	 */
	public float[] getVektor(int j) {
		if (0 <= j && j < getKolom()) {
			
			float[] v = new float[getKolom()];
			for (int i = 0; i < getKolom(); i++) {
				v[i] = getElmt(i, j);
			}
			return v;
		} else {
			return null;
		}
	}
}