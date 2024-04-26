import React, { useState, useEffect } from 'react';
import { TabView, TabPanel } from 'primereact/tabview';
import 'primereact/resources/themes/lara-light-cyan/theme.css';
import 'primeicons/primeicons.css';
import { FilterMatchMode, FilterOperator } from 'primereact/api';
import { FloatLabel } from 'primereact/floatlabel';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { InputTextarea } from 'primereact/inputtextarea';
import { Dropdown } from 'primereact/dropdown';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
//import { CustomerService } from './service/CustomerService';
import { ProgressBar } from 'primereact/progressbar';
import { RadioButton } from 'primereact/radiobutton';

function App() {
  const [value, setValue] = useState();
  const [selectedCity, setSelectedCity] = useState(null);
  const cities = [
    { name: 'New York', code: 'NY' },
    { name: 'Rome', code: 'RM' },
    { name: 'London', code: 'LDN' },
    { name: 'Istanbul', code: 'IST' },
    { name: 'Paris', code: 'PRS' },
  ];

  //const [customers, setCustomers] = useState(null);
  const [filters, setFilters] = useState(null);
  //const [loading, setLoading] = useState(false);
  const [globalFilterValue, setGlobalFilterValue] = useState('');

  const [region, setRegion] = useState('');

  useEffect(() => {
    //CustomerService.getCustomersMedium().then(data => {
      //setCustomers(getCustomers(data));
      //setLoading(false);
    //});
    initFilters();
  }, []);

  //const getCustomers = data => {
    //return [...(data || [])].map(d => {
      //d.date = new Date(d.date);

      //return d;
    //});
  //};

  const formatDate = (value) => {
    return value.toLocaleDateString('en-US', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
    });
};

const formatCurrency = (value) => {
    return value.toLocaleString('en-US', { style: 'currency', currency: 'USD' });
};

const onGlobalFilterChange = (e) => {
    const value = e.target.value;
    let _filters = { ...filters };

    _filters['global'].value = value;

    setFilters(_filters);
    setGlobalFilterValue(value);
};

const initFilters = () => {
    setFilters({
        global: { value: null, matchMode: FilterMatchMode.CONTAINS },
        name: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
        'country.name': { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
        representative: { value: null, matchMode: FilterMatchMode.IN },
        date: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.DATE_IS }] },
        balance: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.EQUALS }] },
        status: { operator: FilterOperator.OR, constraints: [{ value: null, matchMode: FilterMatchMode.EQUALS }] },
        activity: { value: null, matchMode: FilterMatchMode.BETWEEN },
        verified: { value: null, matchMode: FilterMatchMode.EQUALS }
    });
    setGlobalFilterValue('');
};

const renderHeader = () => {
    return (
        <div className="flex flex-wrap align-items-center justify-content-between gap-2">
            <InputText value={globalFilterValue} onChange={onGlobalFilterChange} placeholder="Keyword Search" style={{fontSize: '12px'}}/>
        </div>
    );
};

const countryBodyTemplate = (rowData) => {
    return (
        <div className="flex align-items-center gap-2">
            <img alt="flag" src="https://primefaces.org/cdn/primereact/images/flag/flag_placeholder.png" className={`flag flag-${rowData.country.code}`} style={{ width: '24px' }} />
            <span>{rowData.country.name}</span>
        </div>
    );
};

const representativeBodyTemplate = (rowData) => {
    const representative = rowData.representative;

    return (
        <div className="flex align-items-center gap-2">
            <img alt={representative.name} src={`https://primefaces.org/cdn/primereact/images/avatar/${representative.image}`} width="32" />
            <span>{representative.name}</span>
        </div>
    );
};

const dateBodyTemplate = (rowData) => {
    return formatDate(rowData.date);
};

const balanceBodyTemplate = (rowData) => {
    return formatCurrency(rowData.balance);
};

const statusBodyTemplate = (rowData) => {
    return "";
};

const activityBodyTemplate = (rowData) => {
    return <ProgressBar value={rowData.activity} showValue={false} style={{ height: '6px' }}></ProgressBar>;
};

const paginatorLeft = <Button style={{fontSize: '10px'}} type="button" icon="pi pi-refresh" text />;
const paginatorRight = <Button type="button" icon="pi pi-download" text />;

const header = renderHeader();

  return (
    <div>
      <br />
      <TabView>
        <TabPanel header="Customer Phone List" leftIcon="pi pi-phone mr-2 pi-spin" style={{fontSize: '12px'}}>
          <br/>
          <div className="card">
            <DataTable
              
              paginator
              showGridlines
              
              rows={5}
              rowsPerPageOptions={[5, 10, 25, 50]}
              tableStyle={{minWidth:'50rem'}}
              paginatorTemplate="FirstPageLink PrevPageLink CurrentPageReport NextPageLink LastPageLink RowsPerPageDropdown"
              currentPageReportTemplate="{first} to {last} of {totalRecords}" paginatorClassName='' paginatorLeft={paginatorLeft} paginatorRight={paginatorRight}
              //loading={loading}
              dataKey="id"
              filters={filters}
              globalFilterFields={['name', 'country.name', 'representative.name', 'balance', 'status']}
              header={header}
              style={{fontSize: '12px'}}
              emptyMessage="No customers found."
            >
              <Column field='index' header="Index" />
              <Column field="name" header="First Name" style={{ minWidth: '12rem' }} />
              <Column
                header="Last Name"
                filterField="country.name"
                style={{ minWidth: '12rem' }}
                body={countryBodyTemplate}
              />
              <Column
                header="E-mail"
                style={{ minWidth: '14rem' }}
                body={representativeBodyTemplate}
              />
              <Column
                header="City"
                dataType="date"
                style={{ minWidth: '10rem' }}
                body={dateBodyTemplate}
              />
              <Column
                header="District"
                dataType="numeric"
                style={{ minWidth: '10rem' }}
                body={balanceBodyTemplate}
              />
              <Column
                field="status"
                header="Address"
                style={{ minWidth: '12rem' }}
                body={statusBodyTemplate}
              />
              <Column
                field="activity"
                header="İşlem"
                showFilterMatchModes={false}
                style={{ minWidth: '8rem', textAlign: 'center' }}
                body={activityBodyTemplate}
              />
            </DataTable>
          </div>
        </TabPanel>
        <TabPanel header="Customer" leftIcon="pi pi-users mr-2" style={{fontSize: '12px'}}>
          <div className="formgrid grid">
            <br />
            <FloatLabel>
              <InputText id="firstname" />
              <label for="firstname">First Name</label>
            </FloatLabel>
            <br />
            <FloatLabel>
              <InputText id="lastname" />
              <label for="lastname">Last Name</label>
            </FloatLabel>
            <br />
            <FloatLabel>
              <InputText id="email" />
              <label for="email">E-mail</label>
            </FloatLabel>
            <br />
            <FloatLabel>
              <Dropdown
                inputId="dd-city"
                value={selectedCity}
                onChange={e => setSelectedCity(e.value)}
                options={cities}
                optionLabel="name"
                className="w-full"
                style={{ width: 270 }}
              />
              <label htmlFor="dd-city">Select a City</label>
            </FloatLabel>
            <br />
            <FloatLabel>
              <Dropdown
                inputId="dd-city"
                value={selectedCity}
                onChange={e => setSelectedCity(e.value)}
                options={cities}
                optionLabel="name"
                className="w-full"
                style={{ width: 270 }}
              />
              <label htmlFor="dd-city">Select a District</label>
            </FloatLabel>
            <br />
            <FloatLabel>
              <InputTextarea id="address" value={value} onChange={e => setValue(e.target.value)} rows={5} cols={30} />
              <label htmlFor="address">Address</label>
            </FloatLabel>
            <br />
            <Button label="Save Customer" icon="pi pi-save" style={{ width: 200 }} />
          </div>
        </TabPanel>
        <TabPanel header="District" leftIcon="pi pi-map-marker mr-2" style={{fontSize: '12px'}}>
            <br />
            <div className="card flex justify-content-center">
              <div className="flex flex-wrap gap-3">
                <div className="flex align-items-center">
                  <RadioButton inputId='region1' name='region' value="City" onChange={(e) => setRegion(e.value)} checked={region === 'City'}/>
                  <label htmlFor='region1' className='ml-2'>City</label>
                </div>
                <br/>
                <div className="flex align-items-center">
                  <RadioButton  inputId='region2' name='region' value="District" onChange={(e) => setRegion(e.value)} checked={region === 'District'}/>              
                  <label htmlFor='region2' className='ml-2'>District</label>
                </div>
              </div>
            </div>
            <br/><br/>
            {region === 'City' && 
            <div>
              <FloatLabel>
                <InputText id="cityname" />
                <label for="cityname">City Name</label>
              </FloatLabel>
              <br />
              <Button label="Save City" icon="pi pi-save" style={{ width: 200 }} />
            </div>}
            {region === 'District' &&
            <div>
              <br />
              <FloatLabel>
                <Dropdown
                  inputId="dd-city"
                  value={selectedCity}
                  onChange={e => setSelectedCity(e.value)}
                  options={cities}
                  optionLabel="name"
                  className="w-full"
                  style={{ width: 270 }}
                />
                <label htmlFor="dd-city">Select a District</label>
              </FloatLabel>
              <br/>
              <FloatLabel>
                <InputText id="districtname" />
                <label for="districtname">District Name</label>
              </FloatLabel>
              <br />
              <Button label="Save City" icon="pi pi-save" style={{ width: 200 }} />
              <br/><br/>
              <DataTable
              
              paginator
              showGridlines
              
              rows={5}
              rowsPerPageOptions={[5, 10, 25, 50]}
              tableStyle={{minWidth:'50rem'}}
              paginatorTemplate="FirstPageLink PrevPageLink CurrentPageReport NextPageLink LastPageLink RowsPerPageDropdown"
              currentPageReportTemplate="{first} to {last} of {totalRecords}" paginatorClassName='' paginatorLeft={paginatorLeft} paginatorRight={paginatorRight}
              //loading={loading}
              dataKey="id"
              filters={filters}
              globalFilterFields={['name', 'country.name', 'representative.name', 'balance', 'status']}
              header={header}
              style={{fontSize: '12px'}}
              emptyMessage="No customers found."
            >
              <Column field='index' header="Index" />
              <Column field="city" header="City" style={{ minWidth: '12rem' }} />
              <Column field="district" header="District" style={{ minWidth: '12rem' }} />
            </DataTable>
            </div>  
            }
        </TabPanel>
      </TabView>
    </div>
  );
}

export default App;
