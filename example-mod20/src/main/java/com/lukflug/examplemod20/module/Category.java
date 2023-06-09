package com.lukflug.examplemod20.module;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import com.lukflug.examplemod20.setting.BooleanSetting;
import com.lukflug.examplemod20.setting.ColorSetting;
import com.lukflug.examplemod20.setting.DoubleSetting;
import com.lukflug.examplemod20.setting.IntegerSetting;
import com.lukflug.examplemod20.setting.Setting;
import com.lukflug.examplemod20.setting.StringSetting;
import com.lukflug.panelstudio.setting.ICategory;
import com.lukflug.panelstudio.setting.IClient;
import com.lukflug.panelstudio.setting.IModule;

public enum Category implements ICategory {
	COMBAT("Combat"),EXPLOITS("Exploits"),HUD("HUD"),MISCELLANEOUS("Miscellaneous"),MOVEMENT("Movement"),OTHER("Other"),RENDER("Render"),WORLD("World");
	public final String displayName;
	public final List<Module> modules=new ArrayList<Module>();
	public static Random random=new Random();
	
	private Category (String displayName) {
		this.displayName=displayName;
	}
	
	public static void init() {
		for (Category category: Category.values()) {
			int count=random.nextInt(6)+5;
			for (int i=0;i<count;i++) {
				if (category!=OTHER && category!=HUD) category.modules.add(generateRandomModule());
			}
		}
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public Stream<IModule> getModules() {
		return modules.stream().map(module->module);
	}
	
	public static IClient getClient() {
		return new IClient() {
			@Override
			public Stream<ICategory> getCategories() {
				return Arrays.stream(Category.values());
			}
		};
	}
	
	public static Module generateRandomModule() {
		Module module=new Module(generateRandomName(5,10),generateRandomName(10,20),()->true,random.nextInt(2)==0);
		int count=random.nextInt(6)+5;
		for (int i=0;i<count;i++) {
			module.settings.add(generateRandomSetting());
		}
		return module;
	}
	
	public static Setting<?> generateRandomSetting() {
		String displayName=generateRandomName(5,10);
		String description=generateRandomName(10,20);
		int type=random.nextInt(6);
		int min=random.nextInt(50),max=random.nextInt(50)+50;
		boolean alpha=random.nextInt(2)==0;
		boolean rainbow=random.nextInt(2)==0;
		Color color=new Color(random.nextInt(256),random.nextInt(256),random.nextInt(256),alpha?random.nextInt(256):255);
		switch (type) {
		case 0:
			return new BooleanSetting(displayName,displayName,description,()->true,random.nextInt(2)==0);
		case 1:
			return new ColorSetting(displayName,displayName,description,()->true,alpha,rainbow,color,rainbow?random.nextInt(2)==0:false);
		case 2:
			return new DoubleSetting(displayName,displayName,description,()->true,min,max,random.nextDouble()*(max-min)+min);
		case 4:
			return new IntegerSetting(displayName,displayName,description,()->true,min,max,random.nextInt(max-min+1)+min);
		default:
			return new StringSetting(displayName,displayName,description,()->true,generateRandomName(5,10));
		}
	};
	
	public static String generateRandomName (int min, int max) {
		int length=random.nextInt(max-min+1)+min;
		String s="";
		for (int i=0;i<length;i++) {
			int type=random.nextInt(5);
			switch (type) {
			case 0:
				s+=' ';
				break;
			case 1:
				s+=(char)('A'+random.nextInt(26));
				break;
			default:
				s+=(char)('a'+random.nextInt(26));
			}
		}
		return s;
	}
}
