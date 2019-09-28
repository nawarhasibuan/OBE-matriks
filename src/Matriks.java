/*
	Nama		:Panawar Hasibuan
	Email		:panawarhsb28@gmail.com
	Nama file 	:Matriks.java
	Tanggal		:27 September 2019
*/
import java.util.*;
import java.io.*;

class Matriks {
	/*Data*/
	/*
	 	1) data pada indek (0,0) digunakan untuk menampung faktor skala determinan
		2) kolom-kolom pada baris indeks 0 digunakan untuk menampung lokasi baris leading one (pivot) pada kolom tersebut
		3) baris-baris pada kolom indeks 0 digunakan untuk menampung lokasi kolom leading one (pivot) pada baris tersebut
		4) lokasi pivot diinisialisasi dengan 0 untuk setiap baris/kolom
		5) lokasi pivot diisi 0 jika tidak terdapat pivot pada baris/kolom tersebut
	*/

	//konstanta
	final int MAX = 100;

	//Data Matriks
	private float augm[][];		//matriks augmented
	private int baris;	
	private int kolom;

	/*METHOD*/

	//--Konstruktor--
	public Matriks() {
		augm = new float[MAX+1][MAX+1];
		baris = 0;
		kolom = 0;
		augm[0][0] = 1;
		for (int i = 1; i <= MAX; i++) {
			augm[0][i] = 0;
			augm[i][0] = 0;
		}
	}
	public Matriks(int brs, int kol) {
		augm = new float[MAX+1][MAX+1];
		baris = brs;
		kolom = kol;
		augm[0][0] = 1;
		for (int i = 1; i <= MAX; i++) {
			augm[0][i] = 0;
			augm[i][0] = 0;
		}
	}
	public Matriks(int n) {
		augm = new float[MAX+1][MAX+1];
		baris = n;
		kolom = n;
		augm[0][0] = 1;
		for (int i = 1; i <= n; i++) {
			augm[0][i] = i;
			augm[i][0] = i;
		}
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (i == j) {
					augm[i][j] = 1;
				} else {
					augm[i][j] = 0;
				}
			}
		}
	}

	//--Selektor
	public int getBaris() {
		return this.baris;
	}
	public int getKolom() {
		return this.kolom;
	}
	public float getElmt(int brs, int kol) {
		if (isIndeksValid(brs,kol)) {
			return this.augm[brs][kol];
		} else {
			return 0;
		}
	}

	//--Setter
	public void setBaris(int brs) {
		this.baris = brs;
	}
	public void setKolom(int kol) {
		this.kolom = kol;
	}
	public void setAugm(float newAugm[][],int brs, int kol)
	//mengisi this.augm dengan newAugm
	//mengisi nilai this.baris dan this.kolom setelah pengisian augm selesai
	{
		this.augm = newAugm;
		setBaris(brs);
		setKolom(kol);
	}
	public void setElmt(int brs, int kol, float el) {
		if (isIndeksValid(brs,kol)) {
			this.augm[brs][kol] = el;	
		}
	}

	//--Method Primitif
	public boolean isEmpty() {
		return (getBaris() == 0 && getKolom() == 0);
	}
	public boolean isIndeksValid(int brs, int kol)
	//mengembalikan apakah indeks (brs,kol) valid untuk matriks this
	{
		return (brs <= baris) && (kol <= kolom);
	}
	public void bacaAugm(int brs) throws IOException
	//membaca  isi augm dari keyboard
	{
		for (int i = 1; i <= brs; i++) {
			String input;
			int j;
			float f;

	      	InputStreamReader isr = new InputStreamReader(System.in);
    	  	BufferedReader br = new BufferedReader(isr);
      
      		input = br.readLine();

	    	// create a new scanner with the specified String Object
    		Scanner scanner = new Scanner(input);

    		// use US locale to be able to identify floats in the string
	      	scanner.useLocale(Locale.US);

    	  	// find the next float token and print it
      		// loop for the whole scanner
      		j = 1;
      		while (scanner.hasNext()) {
         		// if the next is a float, print found and the float
         		if (scanner.hasNextFloat()) {
            		f = scanner.nextFloat();
            		setElmt(i,j,f);
            		j++;
         		}
      		}

      		// close the scanner
      		scanner.close();
		}
	}
	public void tulisAugm()
	//menuliskan isi augm ke layar
	/*
		dituliskan dalam bentuk:
		
		[1][1]	[1][2]	...	[1][M]
		[2][1]	[2][2]	...	[2][M]
		.
		.
		.
		[N][1]	[N][2]	...	[N][M]

	*/
	{
		if (isEmpty()) {
			System.out.print("Matriks kosong\n");
		} else {
			for (int i = 1; i <= getBaris(); i++) {
				for (int j = 1; j <= getKolom(); j++) {
					if (getElmt(i,j) == -0) {
						System.out.printf("0.00	");	
					} else {
						System.out.printf("%.2f	",getElmt(i,j));
					}
				}
				System.out.print("\n");
			}
		}
	}
	public void copyMatriks(Matriks M)
	//I.S sembarang
	//I.F menyalin Matriks M ke matriks this
	{
		//set baris dan kolom
		setBaris(M.getBaris());
		setKolom(M.getKolom());
		//set elemen
		for (int i = 0; i <= getBaris(); i++) {
			for (int j = 0; j <= getKolom(); j++) {
				setElmt(i,j,M.getElmt(i,j));
			}
		}
	}
	public Matriks konkatKolom(Matriks M)
	//melakukan konkatonasi matriks this dan M
	{
		//kamus
		Matriks M1;
		//algoritma
		M1 = new Matriks();
		M1.copyMatriks(this);
		M1.setKolom(getKolom()+M.getKolom());
		for (int i = 1; i <= getBaris(); i++) {
			for (int j = 1; j <= M.getKolom(); j++) {
				M1.setElmt(i,getKolom()+j,M.getElmt(i,j));
			}
		}
		return M1;
	}
	public Matriks konsoKolomBack(int kol)
	//konsolidasi matriks pada kolom kol
	//dikembalikan matriks dari kol+1 sampai getKolom()
	//kolom kol tidak disalin
	{
		//kamus
		Matriks M;
		//algoritma
		M = new Matriks(getBaris(),getKolom()-kol);
		for (int i = 1; i <= getBaris(); i++) {
			for (int j = kol+1; j <= getKolom(); j++) {
				M.setElmt(i,j-kol,getElmt(i,j));
			}
		}
		return M;
	}

	public Matriks konsoKolomFront(int kol)
	//konsolidasi matriks pada kolom kol
	//dikembalikan matriks dari 0 sampai kol
	{
		//kamus
		Matriks M;
		//algoritma
		M = new Matriks(getBaris(),kol);
		for (int i = 1; i <= getBaris(); i++) {
			for (int j = 1; j <= kol; j++) {
				M.setElmt(i,j,getElmt(i,j));
			}
		}
		return M;
	}
	public Matriks kaliMatriks(Matriks M)
	//melakukan perkalian matriks (this x M)
	//pekondisi: baris kedua matriks sama
	{
		//kamus
		Matriks M1;
		//algoritma
		M1 = new Matriks(getBaris(),M.getKolom());

		for (int i = 1; i <= getBaris(); i++) {
            for (int j = 1; j <= M.getKolom(); j++) {
                float total = 0; 
                for (int k = 1; k <= getKolom(); k++) {
                    total = total +  (getElmt(i,k)*M.getElmt(k,j));
                }
                M1.setElmt(i,j,total);
            }       
        }
        return M1;
	}
	public Matriks kofMatriks(int brs, int kol)
	//mengembalikan kofaktor matriks terhadap indeks (brs,kol)
	{
		//kamus
		Matriks M;
		//algoritma
		M = new Matriks(getBaris()-1,getKolom()-1);
		//bagian pertama ((0,0) -> (brs,kol))
		for (int i = 1; i < brs; i++) {
			for (int j = 1; j < kol; j++) {
				M.setElmt(i,j,getElmt(i,j));
			}
		}
		//bagian kedua ((0,kol) -> (brs,getKolom()))
		for (int i = 1; i < brs; i++) {
			for (int j = kol+1; j <= getKolom(); j++) {
				M.setElmt(i,j-1,getElmt(i,j));
			}
		}
		//bagian ketiga ((brs,0) -> (getBrs(),kol))
		for (int i = brs+1; i <= getBaris(); i++) {
			for (int j = 1; j < kol; j++) {
				M.setElmt(i-1,j,getElmt(i,j));
			}
		}
		//bagian keempat ((brs,kol) -> [getBaris(),getKolom()])
		for (int i = brs+1; i <= getBaris(); i++) {
			for (int j = kol+1; j <= getKolom(); j++) {
				M.setElmt(i-1,j-1,getElmt(i,j));
			}
		}
		return M;
	}
	public void kaliSkalar(float skalar)
	{
		for (int i = 1; i <= getBaris(); i++) {
			for (int j = 1; j <= getKolom(); j++) {
				setElmt(i,j,getElmt(i,j)*skalar);
			}
		}
	}
	public Matriks adjMatriks()
	//prekondisi :matriks berukuran n*n
	//mengembalikan adjoin matriks
	//prekondisi: matriks berukuran NxN
	{
		//kamus
		Matriks M;
		//algoritma
		if (getBaris() == getKolom()) {	
			M = new Matriks(getBaris(),getKolom());
			for (int i = 1; i <= getBaris(); i++) {
				for (int j = 1; j <= getKolom(); j++) {
					Matriks M1 = kofMatriks(i,j);
					M.setElmt(i,j,M1.detMatriks());
				}
			}
		} else {
			M = new Matriks();
		}
		return M;
	}
	public Matriks inversMatriks()
	//mengembalikan invers matriks
	//mengembalikan matriks kosong jika matriks tidak memiliki invers
	{
		//kamus
		Matriks M,In;
		//algoritma
		if (getBaris() == getKolom() && detMatriks() != 0) {
			In = new Matriks(getBaris());
			M = konkatKolom(In);
			M.makeEFR();
			M = M.konsoKolomBack(getKolom());
		} else {
			M = new Matriks(0,0);
		}
		return M;
	}
	public float detMatriks()
	//prekondisi :matriks berbentuk n*n
	//mengembalikan nilai determinan matriks
	{
		//kamus
		Matriks M;
		float det;
		//algoritma
		M = new Matriks();
		M = this.getEF();
		det = M.getElmt(0,0);
		if (M.getElmt(M.getBaris(),M.getBaris()) == 0) {
			return 0;
		} else {
			return det;
		}
	}

	//--Method OBE
	public void kaliBaris(int brs, float skalar)
	//mengalikan komponen baris ke-brs dengan skalar
	//mengalikan faktor determinan dengan (1/skalar)
	{
		setElmt(0,0,getElmt(0,0)*(1/skalar));
		for (int j = 1; j <= getKolom(); j++) {
			setElmt(brs,j,getElmt(brs,j)*skalar);
		}
	}
	public void swapBaris(int brs1, int brs2)
	//melakukan swap terhadap brs1 dan brs2
	//mengalikan faktor determinan dengan (-1)
	{
		//kamus
		float temp[];
		//algoritma
		setElmt(0,0,getElmt(0,0)*(-1));
		temp = new float[this.getKolom()+1];
		//copy baris1 ke temp
		for (int j = 0; j <= getKolom(); j++) {
			temp[j] = this.getElmt(brs1,j);
		}
		//copy brs2 ke brs1
		for (int j = 0; j <= getKolom(); j++) {
			setElmt(brs1,j,getElmt(brs2,j));
		}
		//copy temp ke brs2
		for (int j = 0; j <= getKolom(); j++) {
			setElmt(brs2,j,temp[j]);
		}
	}
	public void swapKolom(int kol1, int kol2)
	//melakukan swap terhadap brs1 dan brs2
	//mengalikan faktor determinan dengan (-1), tidak dikenakan karna hanya dipakai untuk metode cramer
	{
		//kamus
		float temp[];
		//algoritma
		//setElmt(0,0,getElmt(0,0)*(-1));
		temp = new float[this.getBaris()+1];
		//copy baris1 ke temp
		for (int i = 0; i <= getBaris(); i++) {
			temp[i] = this.getElmt(i,kol1);
		}
		//copy brs2 ke brs1
		for (int i = 0; i <= getBaris(); i++) {
			setElmt(i,kol1,getElmt(i,kol2));
		}
		//copy temp ke brs2
		for (int i = 0; i <= getBaris(); i++) {
			setElmt(i,kol2,temp[i]);
		}
	}
	public void tambahBaris(int brs1, int brs2, float skalar)
	//Rbrs1 <-- Rbrs2*skalar
	{
		//kamus
		float temp[];
		//algoritma
		temp = new float[this.getKolom()+1];
		//copy baris1*skalar ke temp
		temp[0] = 0;
		for (int j = 1; j <= getKolom(); j++) {
			temp[j] = this.getElmt(brs2,j)*skalar;
		}
		//copy brs2 ke brs1
		for (int j = 1; j <= getKolom(); j++) {
			setElmt(brs1,j,temp[j]+getElmt(brs1,j));
		}
	}
	public boolean isEF()
	//mengembalikan apakah Mariks sudah Echelon Form (Gauss Form)
	{
		//kamus
		int i;
		boolean ef;
		//algoritma
		i = 1;
		ef = false;
		while (i <= getBaris() && !ef) {
			ef = (getElmt(i,0) != 0);
			i++;
		}
		return ef;

	}
	public void makeEF()
	//membuat matriks Echelon Form dengan OBE
	{
		//kamus
		int i,k,pivot;
		//algoritma
		pivot = 1;
		i = 1;
		while (pivot <= getKolom() && i < getBaris()) {
			//swap baris jika elemen pertama 0
			if (getElmt(i,pivot) == 0) {
				k = i+1;
				while (k < getBaris() && getElmt(k,pivot) == 0) {
					k++;
				}	//k == getBaris() || getElmet(k,pivot) != 0
				if (getElmt(k,pivot) == 0) {	//semua kolom bernilai 0
					pivot++;
				} else {
					swapBaris(i,k);
				}
			}
			//membuat nol semua bilangan dibawah pivot
			if (pivot <= getKolom() && getElmt(i,pivot) != 0) {	//cek apakah baris sudah di swap
				kaliBaris(i,1/getElmt(i,pivot));	//leading 1 (pivot)
				//posisi leading 1 (pivot)
				setElmt(i,0,(float) pivot);
				setElmt(0,pivot,(float) i);
				for (k = i+1; k <= getBaris(); k++) {
					if (getElmt(k,pivot) != 0) {
						tambahBaris(k,i,getElmt(k,pivot)*(-1));
					}
				}
				i++;	//baris berikutnya, pivot di increase
				pivot++;
			}
		}	//pivot > getKolom() || i == getBaris()
		if (pivot <= getKolom()) {	//i == getBaris(), last row
			while (pivot <= getKolom() && getElmt(i,pivot) == 0) {
				pivot++;
			}
			if (pivot <= getKolom()) {
				kaliBaris(i,1/getElmt(i,pivot));	//leading 1 (pivot)
				//posisi leading 1 (pivot)
				setElmt(i,0,(float) pivot);
				setElmt(0,pivot,(float) i);
			}
		}
	}
	public Matriks getEF()
	//mengembalikan matriks Echelon Form dari this
	{
		//kamus
		Matriks M;
		//algoritma
		M = new Matriks();
		M.copyMatriks(this);
		M.makeEF();
		return M;
	}
	public void makeEFR()
	//membuat matriks Echelon Form tereduksi (Gauss-Jordan Form)
	{
		//kamus
		int i,k,pivot;
		//algoritma
		if (!isEF()) {
			makeEF();
		}
		i = getBaris();
		while (i > 1) {	//traversal dari bawah
			pivot = (int) getElmt(i,0);
			if (pivot > 0 && pivot <= getKolom()) {	//baris memiliki leading one (pivot)
				k = 1;
				while (k < i) {
					tambahBaris(k,i,getElmt(k,pivot)*(-1));
					k++;
				}	//k == 0
				i--; //next baris
			} else {
				i--;
			}
		}	// i == 1
	}
	public Matriks getEFR()
	//mengembalikan matriks Echelon Form tereduksi dari this
	{
		//kamus
		Matriks M;
		//algoritma
		M = new Matriks();
		M.copyMatriks(this);
		M.makeEFR();
		return M;
	}
	public void tulisPivotBaris() {
		int pivot;
		for (int i = 1; i <= getBaris(); i++) {
			pivot = (int) getElmt(i,0);
			System.out.print(pivot + "	");
		}
		System.out.println();
	}
	public void tulisPivotKolom() {
		int pivot;
		for (int i = 1; i <= getKolom(); i++) {
			pivot = (int) getElmt(0,i);
			System.out.print(pivot + "	");
		}
		System.out.println();
	}
}