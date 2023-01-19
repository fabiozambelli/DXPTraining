/**
 * This is the main entry point of the portlet.
 *
 * See https://tinyurl.com/js-ext-portlet-entry-point for the most recent 
 * information on the signature of this function.
 *
 * @param  {Object} params a hash with values of interest to the portlet
 * @return {void}
 */
export default function main({portletNamespace, contextPath, portletElementId}) {
    
    const node = document.getElementById(portletElementId);

    node.innerHTML =`
        <div>
            <span class="tag">${Liferay.Language.get('portlet-namespace')}:</span>
            <span class="value">${portletNamespace}</span>
        </div>
        <div>
            <span class="tag">${Liferay.Language.get('context-path')}:</span>
            <span class="value">${contextPath}</span>
        </div>
        <div>
            <span class="tag">${Liferay.Language.get('portlet-element-id')}:</span>
            <span class="value">${portletElementId}</span>
        </div>
        
        `;
}

YUI().use(
    'aui-alert','aui-node','cookie',
    function(Y) {
        var divEUCookie = '<div id="cookieBanner" class="alert alert-info">I’m sorry, but you have to <a href="#" id="cookieEaten">eat</a> the cookie first!</div>';
        Y.one(document.body).append(divEUCookie);

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