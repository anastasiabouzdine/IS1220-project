package clui;

import java.util.Arrays;

public class Command {
	private String name;
	private int nb_args;
	private String[] args;
	
	public Command(String name, String[] args) {
		this.name = name;
		this.args = args;
		this.nb_args = args.length;
	}
	
	public String getDescription() {
		StringBuilder sb = new StringBuilder(name);
		if (nb_args > 0) {
			sb.append(" <").append(args[0]);
			if (nb_args > 1) {
				for(int i = 1; i < nb_args; i++){
					sb.append(", ").append(args[i]);
				}
			}
			sb.append(">");
		}
		return sb.toString();
	}

	/**************************************************/
	/* Equals, hashcode and toString */
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Command other = (Command) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nb_args != other.nb_args)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + nb_args;
		return result;
	}

	@Override
	public String toString() {
		return "Command [name=" + name + ", nb_args=" + nb_args + ", args=" + Arrays.toString(args) + "]";
	}
	

	/**************************************************/
	/* Equals and hashcode */

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the nb_args
	 */
	public int getNb_args() {
		return nb_args;
	}

	/**
	 * @param nb_args the nb_args to set
	 */
	public void setNb_args(int nb_args) {
		this.nb_args = nb_args;
	}

	/**
	 * @return the args
	 */
	public String[] getArgs() {
		return args;
	}

	/**
	 * @param args the args to set
	 */
	public void setArgs(String[] args) {
		this.args = args;
	}
	

}
