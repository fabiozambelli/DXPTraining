YUI().use(
    'aui-alert','aui-node','cookie',
    function(Y) {
      new Y.Alert(
        {
          closeable: true,
          render: true,
          srcNode: '#cookieBanner'
        }
      );
      
      checkComplianceEUCookie();
          
      function checkComplianceEUCookie(){
          console.log('checkComplianceEUCookie:'+Y.Cookie.get('complianceEUCookie'));
          if (Y.Cookie.get('complianceEUCookie')=='y') {
              Y.one("#cookieBanner").setStyle("display","none");
          } 
      }
      function setComplianceEUCookie(){      
          console.log('setComplianceEUCookie');
          Y.Cookie.set('complianceEUCookie', 'y');
          checkComplianceEUCookie();
      }   
          
      Y.one("#cookieEaten").on('click', function(event) {	
          setComplianceEUCookie();
          Y.one("#cookieBanner").setStyle("display","none");
    });
          
      }
  );
  
  console.group('Sample fragment');
  console.log('fragmentElement', fragmentElement);
  console.log('configuration', configuration);
  console.groupEnd();