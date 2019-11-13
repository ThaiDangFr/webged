// IMAGETTE
// initialisation 
D=document;gk=window.Event?1:0 //Gecko; 
db=!D.documentElement.clientWidth?D.body:D.documentElement //quirk IE6 
var ns6 = document.getElementById&&!document.all

function hideImg(v)
{
	document.getElementById(v).style.display='none';
}

function showImg(v)
{
	gdim=document.getElementById(v);
	gdim.style.display='inline';
	sx=gk?pageXOffset:db.scrollLeft; //scroll h 
	sy=gk?pageYOffset:db.scrollTop; //scroll v 
	fen_x=gk?innerWidth-20:db.clientWidth //l fen?tre 
	fen_y=gk?innerHeight:db.clientHeight  //h fen?tre 
	gdim.style.left=fen_x/2-gdim.offsetWidth/2+sx+'px'; 
	gdim.style.top=fen_y/2-gdim.offsetHeight/2+sy+'px';
}
// IMAGETTE


function rewrite(text) 
{
    if(text!=null)
        document.getElementById("status").innerHTML = text;
}

function customConfirm(text) 
{
    if(!confirm(text))
    {
        return false;
    }
    else
    {
        return true;
    }
}

function selectAll(formObj, blnCheck) 
{
    for(i=0;i<formObj.elements.length;i++)
    {
        if (formObj.elements[i].type == 'checkbox')
        {
            formObj.elements[i].checked = blnCheck;
        }
    }
}

function putFocus(formInst)
{
    if(document.forms.length > 0)
    {	
        for(var i=0; i<document.forms[formInst].length; i++)
	{
	    if(document.forms[formInst].elements[i].type=='text')
            {
	        document.forms[formInst].elements[i].focus();
                break;
            }
        }
    }
}

function clearForm(formInst)
{
    if(document.forms.length > 0)
    {	
        for(var i=0; i<document.forms[formInst].length; i++)
	{
	    if(document.forms[formInst].elements[i].type=="text")
	    {
	        document.forms[formInst].elements[i].value="";
	    }
        }
    }
}

function openWindow(param) 
{
    if(param!=null)
        window.open("popup.jsp?"+param,"","menubar=0,tollbar=0,location=0,directories=0,status=0,scrollbars=0,width=520,height=360");
}

// DYNAMIC MENU BEGIN
function hover(obj){
  if(document.all){
    UL = obj.getElementsByTagName('ul');
    if(UL.length > 0){
      sousMenu = UL[0].style;
      if(sousMenu.display == 'none' || sousMenu.display == ''){
        sousMenu.display = 'block';
      }else{
        sousMenu.display = 'none';
      }
    }
  }
}

function setHover(){
  LI = document.getElementById('menu').getElementsByTagName('li');
  nLI = LI.length;
  for(i=0; i < nLI; i++){
    LI[i].onmouseover = function(){
      hover(this);
    }
    LI[i].onmouseout = function(){
      hover(this);
    }
  }
}
// DYNAMIC MENU END



/***************
 * RIGHT CLICK *
 ***************/
/*
function openMenu(divId)
{
	var elem = document.getElementById(divId);
	elem.style.display='inline';
	elem.style.position='absolute';
	elem.style.background='yellow';
	elem.style.padding='20px';
		
			
	if(ns6)
  	{
	    elem.style.left = divId.clientX+document.body.scrollLeft;
	    elem.style.top = divId.clientY+document.body.scrollTop;
	    
	    alert(parseInt(elem.style.left));
  	} else
  	{
	    elem.style.pixelLeft = event.clientX+document.body.scrollLeft-50;
	    elem.style.pixelTop = event.clientY+document.body.scrollTop;
  	}
  	
  		
  	elem.onmouseout = function() 
  	{
	  	elem.style.display='none';
  	}  	  
}
*/