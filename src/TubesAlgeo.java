/*
	Nama		:Panawar Hasibuan
	Email		:panawarhsb28@gmail.com
	Nama file 	:TubesAlgeo.java
	Tanggal		:27 September 2019
*/

import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

class TubesAlgeo {
	private static Matriks aug,out;
	private static String str;			//untuk dituliskan ke layar dan/atau ke file
	private static boolean noSolusi,trivial;	//cek jenis sulusi
	private static float hasil[];
	public static void main(String[] args) throws IOException {
		//KAMUS
		//penampung jawaban
		float tunggal[];	//solusi tunggal
		int nonTrivial[];	//menampung variable bebas solusi banyak
		float det;			//untuk determinan
		float x,y;
		//input
		String[] fout = {"sploutput.txt","detoutput.txt","inversoutput.txt","kofoutput.txt","adjoutput.txt","interoutput.txt"};
		String[] fin = {"splinput.txt","detinput.txt","inversinput.txt","kofinput.txt","adjinput.txt","interinput.txt"};
		String nmfile;
		String temp;	//input variable int
		int n,m;		//ukuran matriks
		char jenisinput;	//jenis input (keyboard/file)

		//ALGORITMA
		//inisialisasi
		det = 0;
		temp = new String();
		m = 0;
		n = 0;
		jenisinput = '0';
		aug = new Matriks();
		out = new Matriks();
		str = new String(" ");

		label1: while (true) {
			noSolusi = true;
			trivial = true;
			mainMenu();
			String inMenu = strInput();

			//seitch mainMenu
			switch (inMenu) {
				case "1":
					subMenu();
					subMenuSPL();
					String inSubMenu = strInput();
					if (inSubMenu.equals("1") || inSubMenu.equals("2") || inSubMenu.equals("3")) {
						jenisinput = inputData();
						if (jenisinput == 'k' || jenisinput == 'K') {
							System.out.print("nilai m:");
							temp = strInput();
							try {
								m = Integer.parseInt(temp);
							} catch (NumberFormatException nfe) {
								System.out.println("bukan bilangan bulat");
							}
							System.out.print("nilai n:");
							temp = strInput();
							try {
								n = Integer.parseInt(temp);
							} catch (NumberFormatException nfe) {
								System.out.println("bukan bilangan bulat");
							}
							aug = new Matriks(m,n+1);
							System.out.println("Masukkan nilai a[i,j] dan b[i] dalam bentuk matriks augmented:");
							aug.bacaAugm(m);
						} else {
							System.out.print("Masukkan nama file: ");
							nmfile = strInput();
							if (!nmfile.endsWith(".txt")) {
								System.out.println("akan dipakai file default, file harus bertipe .txt");
								nmfile = fin[0];
							}
							aug = inFileAugm("../test/"+nmfile);
						}
						//Matriks aug berisi input user
						switch (inSubMenu) {
							case "1":
								hasil = gaussJordan(aug);
								str = persSolusi(hasil);
								break;
							case "2":
								hasil = metodeBalikan(aug);
								str = persSolusi(hasil);
								break;
							case "3":
								hasil = metodeCramer(aug);
								str = persSolusi(hasil);
								break;
						}

					} else {	//default
						System.out.println("pilihan menu salah");
					}
					break;

				case "2":
					jenisinput = inputData();
					if (jenisinput == 'k' || jenisinput == 'K') {
						System.out.print("nilai n:");
						temp = strInput();
						try {
							n = Integer.parseInt(temp);
						} catch (NumberFormatException nfe) {
							System.out.println("bukan bilangan bulat");
						}
						aug = new Matriks(n,n);
						System.out.println("Masukkan nilai a[i,j] matriks:");
						aug.bacaAugm(n);
					} else {
							System.out.print("Masukkan nama file: ");
							nmfile = strInput();
							if (!nmfile.endsWith(".txt")) {
								System.out.println("akan dipakai file default, file harus bertipe .txt");
								nmfile = fin[1];
							}
							aug = inFileAugm("../test/"+nmfile);
					}
					//Matriks aug berisi input user
					det = aug.detMatriks();
					str = det+" ";
					break;

				case "3":
					subMenu();
					subMenuBalikan();
					inSubMenu = strInput();
					if (inSubMenu.equals("1") || inSubMenu.equals("2")) {
							jenisinput = inputData();
							if (jenisinput == 'k' || jenisinput == 'K') {
							System.out.print("nilai n:");
							temp = strInput();
							try {
								n = Integer.parseInt(temp);
							} catch (NumberFormatException nfe) {
								System.out.println("bukan bilangan bulat");
							}
							aug = new Matriks(n,n);
							System.out.println("Masukkan nilai a[i,j] matriks:");
							aug.bacaAugm(n);
						} else {
							System.out.print("Masukkan nama file: ");
							nmfile = strInput();
							if (!nmfile.endsWith(".txt")) {
								System.out.println("akan dipakai file default, file harus bertipe .txt");
								nmfile = fin[2];
							}
							aug = inFileAugm("../test/"+nmfile);
						}
						//Matriks aug berisi input user
						if (inSubMenu.equals("1")) {
							//out = aug.inversMatriks();
							Matriks I = new Matriks(aug.getKolom());
							out = aug.konkatKolom(I);
							System.out.println();
							System.out.println();
							out.makeEFR();
							System.out.println();
							out = out.konsoKolomBack(out.getBaris());
						} else {
							det = aug.detMatriks();
							if (det == 0) {
								out = new Matriks();
							} else {
								out = aug.adjMatriks();
								out.kaliSkalar(1/det);
							}
						}
					} else {	//default
						System.out.println("pilihan menu salah");
					}
					break;

				case "4":
					jenisinput = inputData();
					if (jenisinput == 'k' || jenisinput == 'K') {
						System.out.print("nilai m:");
						temp = strInput();
						try {
							m = Integer.parseInt(temp);
						} catch (NumberFormatException nfe) {
							System.out.println("bukan bilangan bulat");
						}
						System.out.print("nilai n:");
						temp = strInput();
						try {
							n = Integer.parseInt(temp);
						} catch (NumberFormatException nfe) {
							System.out.println("bukan bilangan bulat");
						}
						aug = new Matriks(m,n);
						System.out.println("Masukkan nilai a[i,j] matriks:");
						aug.bacaAugm(m);
					} else {
						System.out.print("Masukkan nama file: ");
						nmfile = strInput();
						if (!nmfile.endsWith(".txt")) {
							System.out.println("akan dipakai file default, file harus bertipe .txt");
							nmfile = fin[3];
						}
						aug = inFileAugm("../test/"+nmfile);	
					}
					System.out.print("nilai i:");
					temp = strInput();
					try {
						m = Integer.parseInt(temp);
					} catch (NumberFormatException nfe) {
						System.out.println("bukan bilangan bulat");
					}
					System.out.print("nilai j:");
					temp = strInput();
					try {
						n = Integer.parseInt(temp);
					} catch (NumberFormatException nfe) {
						System.out.println("bukan bilangan bulat");
					}
					out = aug.kofMatriks(m,n);
					break;

				case "5":
					jenisinput = inputData();
					if (jenisinput == 'k' || jenisinput == 'K') {
						System.out.print("nilai n:");
						temp = strInput();
						try {
							n = Integer.parseInt(temp);
						} catch (NumberFormatException nfe) {
							System.out.println("bukan bilangan bulat");
						}
						aug = new Matriks(n,n);
						System.out.println("Masukkan nilai a[i,j] matriks:");
						aug.bacaAugm(n);
					} else {
						System.out.print("Masukkan nama file: ");
						nmfile = strInput();
						if (!nmfile.endsWith(".txt")) {
							System.out.println("akan dipakai file default, file harus bertipe .txt");
							nmfile = fin[4];
						}
						aug = inFileAugm("../test/"+nmfile);							
					}
					out = aug.adjMatriks();
					break;

				case "6":
					jenisinput = inputData();
					if (jenisinput == 'k' || jenisinput == 'K') {
						System.out.print("nilai n:");
						temp = strInput();
						try {
							n = Integer.parseInt(temp);
						} catch (NumberFormatException nfe) {
							System.out.println("bukan bilangan bulat");
						}
						aug = new Matriks(n,n+1);
						for (int i = 0; i < n; i++) {
							temp = strInput();
							Scanner scanner = new Scanner(temp);

							// use US locale to be able to identify floats in the string
					      	scanner.useLocale(Locale.US);

						  	// find the next float token and print it
					  		// loop for the whole scanner
					  		x = scanner.nextFloat();
					  		y = scanner.nextFloat();
					  		//isi data baris ke-i+1;
					  		aug.setElmt(i+1,1,1);
					  		for (int j = 1; j < aug.getKolom(); j++) {
					  			aug.setElmt(i+1,j+1,aug.getElmt(i+1,j)*x);
					  		}
					  		aug.setElmt(i+1,aug.getKolom(),y);	//augmented matriks
						}
					} else {

						System.out.print("Masukkan nama file: ");
						nmfile = strInput();
						if (!nmfile.endsWith(".txt")) {
							System.out.println("akan dipakai file default, file harus bertipe .txt");
							nmfile = fin[5];
						}
						aug = inFileInter("../test/"+nmfile);	
					}
					out = aug.getEFR();
					hasil = new float[out.getBaris()];
					for (int i=0; i<hasil.length; i++) {
						hasil[i] = out.getElmt(i+1,out.getKolom());
					}
					//hasil = gaussJordan(aug);
					for (int i = 0; i < hasil.length; i++) {
						System.out.printf("%.2f	",hasil[i]);
					}
					System.out.println();
					str = persInterpolasi(hasil);
					while (true) {
							System.out.print("nilai x:");
							temp = strInput();
							try {
								x = Float.parseFloat(temp);
							} catch (NumberFormatException nfe) {
								System.out.println("bukan bilangan desimal");
								break;
							}
							y = nilaiInterpolasi(x);
							System.out.printf("nilai taksiran: %.2f\n",y);
					}
					break;

				case "7":
					break label1;

				default:
					System.out.println("pilihan menu salah");
			}	//end switch

			//output layar
			if (!aug.isEmpty()) {
				System.out.println("matriks asal:");
				aug.tulisAugm();
				System.out.println("solusi: \n"+str+"\n");
				if (!out.isEmpty()) {
					out.tulisAugm();
				}
				System.out.println("\nsimpan solusi ke file (y/t) : ");
				temp = strInput();
				if (temp.equals("y") || temp.equals("Y")) {
					outFile("../output/"+fout[Integer.parseInt(inMenu)-1]);
				}
				aug = new Matriks();
				out = new Matriks();
				str = new String(" ");
			}
		}	//end while-loop

		System.out.println("\nTERIMA KASIH");
	}	//end main method


	//method static
	static void mainMenu() {
		System.out.println("Menu");
		System.out.println("1. Sistem Persamaan Linier");
		System.out.println("2. Determinan");
		System.out.println("3. Matriks Balikan");
		System.out.println("4. Matriks Kofaktor");
		System.out.println("5. Adjoin");
		System.out.println("6. Interpolasi Polinom");
		System.out.println("7. Keluar");
	}
	static void subMenu() {
		System.out.println("1. Metode Eleminasi Gauss atau Gauss-Jordan");
	}
	static void subMenuSPL() {
		System.out.println("2. Metode Matriks Balikan");
		System.out.println("3. Metode Cramer");
	}
	static void subMenuBalikan() {
		System.out.println("2. Metode Adjoin");
	}

	static char inputData() throws IOException {
		char ch;
		ulang: while (true) {
			System.out.println("input keyboard/file (k/f): ");
			ch = charInput();
			if (ch == 'f' || ch == 'F' || ch == 'k' || ch == 'K') {
				break ulang;
			}else {
				System.out.println("pilihan salah");
			}
		}
		return ch;
	}

	//FILE
	static Matriks inFileAugm(String file) {
		//kamus
		FileInputStream finput = null;
		String augm,strKol;
		Matriks M;
		float f;
		int data,brs,kol,idx;
		//algoritma
		brs = kol = 0;
		augm = new String();
		//membuka file
		try {
			finput = new FileInputStream(file);
		} catch (FileNotFoundException fnfe) {
			System.out.println("file tidak ditemukan.");
			return new Matriks();
		}
		//membaca file
		try {
			while ((data = finput.read()) != -1) {
				if ((char) data == '\n') {
					brs++;		//baris matriks
				}
				augm = augm + (char) data;
			}
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
		//kolom matriks
		idx = augm.indexOf('\n');
		strKol = augm.substring(0,idx);
		//olah data
		// create a new scanner with the specified String Object
		Scanner scanner = new Scanner(strKol);

		// use US locale to be able to identify floats in the string
      	scanner.useLocale(Locale.US);

	  	// find the next float token and print it
  		// loop for the whole scanner
  		while (scanner.hasNext()) {
     		// if the next is a float, print found and the float
     		if (scanner.hasNextFloat()) {
     			kol++;
        		f = scanner.nextFloat();
     		}
  		}

  		M = new Matriks(brs,kol);

		// create a new scanner with the specified String Object
		scanner = new Scanner(augm);

		// use US locale to be able to identify floats in the string
      	scanner.useLocale(Locale.US);

      	for (int i = 1; i <= brs; i++) {
      		for (int j = 1; j <= kol; j++) {
      			f = scanner.nextFloat();
      			M.setElmt(i,j,f);
      		}
      	}

  		// close the scanner
  		scanner.close();
		//tutup file
		try {
			finput.close();
		} catch (IOException ioe) {}

		return M;
	}
	static Matriks inFileInter(String file) {
		//kamus
		FileInputStream finput = null;
		String augm;
		Matriks M;
		float x,y;
		int data,n;
		//algoritma
		n = 0;
		augm = new String();
		//membuka file
		try {
			finput = new FileInputStream(file);
		} catch (FileNotFoundException fnfe) {
			System.out.println("file tidak ditemukan.");
			return new Matriks();	//keluar method
		}
		//membaca file
		try {
			while ((data = finput.read()) != -1) {
				if ((char) data == '\n') {
					n++;		//baris matriks
				}
				augm = augm + (char) data;
			}
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
		//olah data
		M = new Matriks(n,n+1);
		// create a new scanner with the specified String Object
		Scanner scanner = new Scanner(augm);

		// use US locale to be able to identify floats in the string
      	scanner.useLocale(Locale.US);

      	for (int i = 1; i <= n; i++) {
      		x = scanner.nextFloat();
      		y = scanner.nextFloat();
      		M.setElmt(i,1,1);
      		for (int j = 2; j <= n; j++) {
      			M.setElmt(i,j,M.getElmt(i,j-1)*x);
      		}
      		M.setElmt(i,M.getKolom(),y);
      	}

  		// close the scanner
  		scanner.close();
		//tutup file
		try {
			finput.close();
		} catch (IOException ioe) {}

		return M;
	}
	static void outFile(String file) {
		//kamus
		FileOutputStream foutput = null;
		String data = str;
		//algoritma

		//mempersiapkan data
		if (str.equals(" ")) {
			data = new String();
		} else {
			data = data.concat("\n");
		}
		if (!out.isEmpty()) {
			for (int i = 1; i <= out.getBaris(); i++) {
				for (int j = 1; j <= out.getKolom(); j++) {
					data = data.concat(out.getElmt(i,j)+" ");
				}
				data = data.concat("\n");
			}
		}

		//buka file
		try {
			foutput = new FileOutputStream(file);
		} catch (FileNotFoundException fnfe) {
			System.out.println("File tidak dapat terbentuk.");
			return;	//keluar merhode
		}

		//menulis ke file
		try {
			for (int i=0; i<data.length(); i++) {
				//dikonversi perkarakter
				foutput.write((int) data.charAt(i));
			}
		} catch(IOException ioe) {
			System.out.println(ioe.getMessage());
			return;
		}


		//tutup file
		try {
			foutput.close();
		} catch (IOException ioe) {}
	}

	//INPUT type dasar
	static String strInput() throws IOException {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String str1 = br.readLine();
		return str1;
	}
	static char charInput() throws IOException {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		char ch = (char) br.read();
		return ch;
	}

	//PENGOLAH
	static float[] gaussJordan(Matriks aug) {
		//kamus
		float hasil[];
		int count;
		//algoritma
		out = aug.getEFR();
		//telusur pivot dari bawah
		int brs = out.getBaris();
		int pivot = (int) out.getElmt(brs,0);
		while (pivot == 0) {
			brs--;
			pivot = (int) out.getElmt(brs,0);
		}
		noSolusi = (pivot == out.getKolom());
		trivial = (!noSolusi && out.getBaris() == (out.getKolom()-1) && brs == out.getBaris());
		hasil = new float[out.getKolom()-1];
		for (int i = 0; i < out.getBaris(); i++) {	//solusi tunggal atau no solusi
			hasil[i] = out.getElmt(i+1,out.getKolom());
		}
		if (!noSolusi && !trivial) {	//solusi banyak
			count = 0;
			for (int j = 1; j <= out.getKolom(); j++) {
				if (out.getElmt(0,j) == 0) {
					count++;
				}
			}
			hasil = new float[count];
			int j = 1;
			int i = 0;
			while (j <= out.getKolom()) {
				if (out.getElmt(0,j) == 0) {
					hasil[i] = j;
					i++;
				}
				j++;
			}
		}
		return hasil;
	}
	static float[] metodeBalikan(Matriks aug) {
		//kamus
		Matriks M;
		float hasil[];
		//algoritma
		hasil = new float[5];
		M = aug.konsoKolomFront(aug.getKolom()-1);
		out = M.inversMatriks();
		noSolusi = out.isEmpty();
		trivial = !out.isEmpty();
		if (trivial) {
			M = aug.konsoKolomBack(aug.getKolom()-1);
			out = out.kaliMatriks(M);
			hasil = new float[out.getBaris()];
			for (int i = 0; i < out.getBaris(); i++) {
				hasil[i] = out.getElmt(i+1,1);
			}
		}
		return hasil;
	}
	static float[] metodeCramer(Matriks aug) {
		//kamus
		Matriks M;
		float hasil[];
		float mainDet,subDet;
		//algoritma
		hasil = new float[5];
		out = aug.konsoKolomFront(aug.getKolom()-1);
		mainDet = out.detMatriks();
		trivial = (mainDet != 0);
		noSolusi = (mainDet == 0);
		if (trivial) {
			hasil = new float[out.getKolom()];
			M = new Matriks();
			for (int i = 0; i < hasil.length; i++) {
				M.copyMatriks(aug);
				M.swapKolom(i+1,M.getKolom());
				M = M.konsoKolomFront(M.getKolom()-1);
				subDet = M.detMatriks();
				hasil[i] = subDet/mainDet;
			}
		}
		return hasil;
	}
	static String persSolusi(float hasil[]) {
		//kamus
		DecimalFormat decimal = new DecimalFormat("#.00");
		String str  = new String();
		//algortima
		if (!noSolusi && !trivial) {	//solusi banyak
			for (int j = 0; j <= out.getKolom(); j++) {
				if (out.getElmt(0,j) == 0) {	//solusi bebas
					str = str.concat("x"+j+"="+"x"+j+"\n");
				} else {	//solusi terikat
					str = str.concat("x"+j+"="+out.getElmt((int)out.getElmt(0,j),out.getKolom()));
					for (int k = 0; k < hasil.length; k++) {
						if (j < hasil[k]) {
							if (hasil[k] > 0) {
								str = str.concat("+"+out.getElmt((int)out.getElmt(0,j),(int)hasil[k])+"x"+(int)hasil[k]);
							} else if (hasil[k] < 0) {
								str = str.concat(out.getElmt((int)out.getElmt(0,j),(int)hasil[k])+"x"+(int)hasil[k]);
							}
						}
					}
					str = str.concat("\n");
				}
			}
		} else if (trivial) {
			for (int i = 0; i < hasil.length-1; i++) {
				str = str.concat("x"+(i+1)+"="+hasil[i]+"\n");
			}
			str = str.concat("x"+hasil.length+"="+hasil[hasil.length-1]+"\n");
		} else if (noSolusi) {
			str = str.concat("tidak ada solusi\n");
		}
		return str;
	}
	static String persInterpolasi(float hasil[]) {
		DecimalFormat decimal = new DecimalFormat("#0.00");
		boolean koma;
		String equation;
		equation = new String();
		koma = (hasil[0] < 1 && hasil[0] > 1);
		if (koma) {
			if (hasil[0] > 0) {
				equation = equation.concat("0"+decimal.format(hasil[0])+" ");
			} else if (hasil[0] < 0) {
				equation = equation.concat(decimal.format(hasil[0])+" ");
			}
		} else {
			if (hasil[0] > 0) {
				equation = equation.concat(decimal.format(hasil[0])+" ");
			} else if (hasil[0] < 0) {
				equation = equation.concat(decimal.format(hasil[0])+" ");
			}
		}
		koma = (hasil[1] < 1 && hasil[1] > 1);
		if (koma) {
			if (hasil[1] > 0) {
				equation = equation.concat("+ 0"+decimal.format(hasil[1])+"x ");
			} else if (hasil[1] < 0) {
				equation = equation.concat("0"+decimal.format(hasil[1])+"x ");
			}
		} else {
			if (hasil[1] > 0) {
				equation = equation.concat("+ "+decimal.format(hasil[1])+"x ");
			} else if (hasil[1] < 0) {
				equation = equation.concat(decimal.format(hasil[1])+"x ");
			}
		}
		for (int i = 2; i < hasil.length; i++) {
			koma = (hasil[i] < 1 && hasil[i] > 1);
			if (!koma) {
				if (hasil[i] > 0) {
					equation = equation.concat("+"+decimal.format(hasil[i])+"x^("+i+") ");
				} else {
					equation.concat(decimal.format(hasil[i])+"x^("+i+") ");}
			} else {
				if (hasil[i] > 0) {
					equation = equation.concat("+0"+decimal.format(hasil[i])+"x^("+i+") ");
				} else {
					equation.concat("0"+decimal.format(hasil[i])+"x^("+i+") ");}
			}
		}

		return equation;
	}
	static float nilaiInterpolasi(float x) {
		//kamus
		float f,pangkat;
		//algoritma
		f = 0;
		pangkat = 1;
		for (int i=0; i<hasil.length; i++) {
			f += (hasil[i]*pangkat);
			pangkat *= x;
		}
		return f;
	}
}