import React from 'react';
import { TabView, TabPanel } from 'primereact/tabview';
import 'primereact/resources/themes/lara-light-cyan/theme.css';
import 'primeicons/primeicons.css';
import { District } from './components/District';
import Customer from './components/Customer';
import CustomerPhoneList from './components/CustomerPhoneList';


function App() {

  return (
    <div>
      <br />
      <TabView>
        <TabPanel header="Customer Phone List" leftIcon="pi pi-phone mr-2 pi-spin" style={{fontSize: '12px'}}>
          <br/>
          <CustomerPhoneList />
        </TabPanel>
        <TabPanel header="Customer" leftIcon="pi pi-users mr-2" style={{fontSize: '12px'}}>
          <Customer />
        </TabPanel>
        <TabPanel header="District" leftIcon="pi pi-map-marker mr-2" style={{fontSize: '12px'}}>
            <br />
            <District/>
        </TabPanel>
      </TabView>
    </div>
  );
}

export default App;
