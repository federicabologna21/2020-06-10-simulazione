package it.polito.tdp.imdb.db;

public class Genere {

	String genere;

	public Genere(String genere) {
		super();
		this.genere = genere;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	@Override
	public String toString() {
		return  genere ;
	}
	
	
	
	
}
