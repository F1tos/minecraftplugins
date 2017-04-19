package socketing;


import net.minecraft.server.v1_8_R3.InventorySubcontainer;

public class ANSMenu extends InventorySubcontainer {

	public ANSMenu()
	{
		super("Добавление нового слота", true, 27);
	}
	
	public ANSMenu(String s, boolean flag, int i) 
	{
		super(s, flag, i);
	}

}
