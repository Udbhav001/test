package com.fresco;

import java.util.*;

class Library
{

    String bookName;
    String author;
    Library()
    {
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.bookName);
        hash = 83 * hash + Objects.hashCode(this.author);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Library other = (Library) obj;
        if (!Objects.equals(this.bookName, other.bookName)) {
            return false;
        }
        if (!Objects.equals(this.author, other.author)) {
            return false;
        }
        return true;
    }

	public String getAuthor()
	{
		return this.author;
	}
	public String getBookName()
	{
		return this.bookName;
	}

    Library(String bookName,String author)
    {
        this.bookName=bookName;
        this.author=author;
    }
    public HashMap<Integer,Library> createLibraryMap(String booksInLibrary)
    {
        HashMap<Integer,Library> map=new HashMap<>();
        String data[]=booksInLibrary.split("\\|");
        for(String x : data)
        {

            String a[]=x.split(",");
            map.put(Integer.parseInt(a[0]),new Library(a[1],a[2]));
        }

        return map;
    }
    public HashMap<Integer,Integer> createUserMap(String borrowedUsers)
    {
        HashMap<Integer,Integer> map=new HashMap<>();
        String data[]=borrowedUsers.split("\\|");
        for(String x : data)
        {
            String a[];
	    a=x.split(",");
            map.put(Integer.parseInt(a[0]),Integer.parseInt(a[1]));
        }
        return map;
    }


    public String getQuery(String booksInLibrary,String borrowedUsers,String query)
    {
        HashMap<Integer,Library> map1=createLibraryMap(booksInLibrary);
        HashMap<Integer,Integer> map2=createUserMap(borrowedUsers);
        int n=Integer.parseInt(query.substring(0,1));
        int id=0;
        String name="";
        String ans="";
        Set<Integer> set=null;
        switch(n)
        {
            case 1:
                id=Integer.parseInt(query.substring(2));
                if(map2.containsKey(id))
                ans="No Stock\nIt is owned by "+map2.get(id)+"\n";
                else
                ans="It is available\nAuthor is "+map1.get(id).getAuthor()+"\n";
                break;
            case 2:
                id=Integer.parseInt(query.substring(2));
                set = map2.keySet();
                for(int x : set)
                {
                    if(map2.get(x)==id)
                    {
                      ans+=x+" "+map1.get(x).getBookName()+"\n";
                    }
                }
                break;
            case 3:
                name=query.substring(2);
                set=map1.keySet();
                int ar[]=new int[10];
                int pos=0;
                int c=0,d=0;
                for(int x : set)
                {
                    if(map1.get(x).getBookName().equalsIgnoreCase(name))
                    {
                        ar[pos++]=x;
                        c++;
                    }
                }
                set=map2.keySet();
                for(int x : set)
                {
                	for(int i=0;i<10;i++)
                		if(ar[i]!=0 && ar[i]==x)
	                    {
	                        d++;
	                        ar[i]=0;
	                        break;
	                    }
                }
                ans=d+" out\n"+(c-d)+" in\n";
                break;
            case 4:
                name=query.substring(2);
                set=map1.keySet();
                for(int x : set)
                {
                    if(map1.get(x).getAuthor().equalsIgnoreCase(name))
                        ans+=map1.get(x).getBookName()+"\n";
                }
                break;
            case 5:
                name=query.substring(2);
                set=map1.keySet();
                int len=name.length();
                for(int x : set)
                {
                    String str = map1.get(x).getBookName();
                    for(int i=0;i<str.length()-len+1;i++)
                    {
                    	if(str.substring(i,i+len).equalsIgnoreCase(name))
                            ans+=x+" "+map1.get(x).getBookName()+"\n";
                	}
                }
                break;
        }
        return ans;
    }
}
