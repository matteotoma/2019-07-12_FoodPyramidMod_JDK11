package it.polito.tdp.food.model;

public class Cucina {
	
	private Integer id;
	private boolean libera;
	
	public Cucina(Integer id, boolean libera) {
		super();
		this.id = id;
		this.libera = libera;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isLibera() {
		return libera;
	}

	public void setLibera(boolean libera) {
		this.libera = libera;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cucina other = (Cucina) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
