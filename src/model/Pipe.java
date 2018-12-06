package model;

import java.util.Map;

public class Pipe  implements Cloneable{
	public int pipeIndex;
	public int pipeColumn;
	public char pipeType;
	public char[] pipesFamily;
	
	public Pipe(int pipeIndex, int pipeColumn, char pipeType, char[] pipesFamily)
	{
		this.pipeIndex = pipeIndex;
		this.pipeColumn = pipeColumn;
		this.pipeType = pipeType;
		this.pipesFamily = pipesFamily;
	}
	
	protected void rotate()
	{
		for (int i = 0; i < this.pipesFamily.length; i++)
		{
			if (this.pipeType == this.pipesFamily[i] && (i + 1) < this.pipesFamily.length)
			{
				this.pipeType = this.pipesFamily[i + 1];
				break;
			}
			else if(this.pipeType == this.pipesFamily[i])
			{
				this.pipeType = this.pipesFamily[0];
				break;
			}
		}
	}
	
	public boolean isConnected(Pipe neighborPipeToCheck, Map<Character, char[]> availableConnectionsByDirection)
	{
		if(neighborPipeToCheck == null)
		{
			return false;
		}
		char[] values = availableConnectionsByDirection.get(this.pipeType);
		for(char c : values)
		{
			if(c == neighborPipeToCheck.pipeType)
			{
				return true;
			}
		}
		return false;
	}
	@Override
	public Pipe clone() throws CloneNotSupportedException
	{
		return (Pipe) super.clone();
	}

}
