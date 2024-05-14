import React, { useState, useEffect } from 'react';
import axios from 'axios';
import 'primereact/resources/themes/lara-light-cyan/theme.css';
import 'primeicons/primeicons.css';
import { FloatLabel } from 'primereact/floatlabel';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { Dropdown } from 'primereact/dropdown';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { RadioButton } from 'primereact/radiobutton';

export const District = () => {
  const [selectedCity, setSelectedCity] = useState(null);
  const [filters, setFilters] = useState(null);
  const [globalFilterValue, setGlobalFilterValue] = useState('');
  const [district, setDistrict] = useState([]);
  const [cityName, setCityName] = useState('');
  const [cityDescription, setCityDescription] = useState('');

  const [districtName, setDistrictName] = useState('');
  const [districtDescription, setDistrictDescription] = useState('');

  const [region, setRegion] = useState('');
  const [data, setData] = useState(null);

  useEffect(() => {
    if (region === 'District') {
      axios
        .get('http://localhost:8080/api/district')
        .then(response => {
          setData(response.data);
        })
        .catch(error => {
          console.log(error);
        });

      axios
        .get('http://localhost:8080/api/district/code/-1')
        .then(response => {
          setDistrict(response.data);
          console.log('Data : ', response?.data);
        })
        .catch(error => {
          console.log(error);
        });
    }
  }, [region]);

  const renderHeader = () => {
    return (
      <div className="flex flex-wrap align-items-center justify-content-between gap-2">
        <InputText value={globalFilterValue} onChange={onGlobalFilterChange} placeholder="Keyword Search" style={{ fontSize: '12px' }} />
      </div>
    );
  };
  const onGlobalFilterChange = e => {
    const value = e.target.value;
    let _filters = { ...filters };

    _filters['global'].value = value;

    setFilters(_filters);
    setGlobalFilterValue(value);
  };

  const paginatorLeft = <Button style={{ fontSize: '10px' }} type="button" icon="pi pi-refresh" text />;
  const paginatorRight = <Button type="button" icon="pi pi-download" text />;

  const header = renderHeader();

  const saveCity = () => {
    const values = {
      name: cityName,
      description: cityDescription,
      code: -1,
    };

    axios
      .post('http://localhost:8080/api/district', values)
      .then(response => {
        console.log('Yeni City Veri: ', response.data);
      })
      .catch(error => {
        console.log(error);
      });
    setCityName('');
    setCityDescription('');
  };

  const saveDistrict = () => {
      const values = {
        name: districtName,
        description: districtDescription,
        code: selectedCity?.id,
      }
    axios
      .post('http://localhost:8080/api/district', values)
      .then(response => {
        console.log('Yeni District Veri: ', response.data);
      })
      .catch(error => {
        console.log(error);
      });
      setDistrictName('');
      setDistrictDescription('');
      setSelectedCity(null);

      window.location.reload();

  };

  const actionBodyTemplate = (rowData) => {
    return <Button type='button' icon="pi pi-trash" size='small' severity='danger' outlined aria-label="Cancel" rounded tooltip='Customer tarafından kullanılmayan satırlar silinebilecektir.' tooltipOptions={{position:"left"}}/>
  }

  return (
    <div>
      <div className="card flex justify-content-center">
        <div className="flex flex-wrap gap-3">
          <div className="flex align-items-center">
            <RadioButton inputId="region1" name="region" value="City" onChange={e => setRegion(e.value)} checked={region === 'City'} />
            <label htmlFor="region1" className="ml-2">
              City
            </label>
          </div>
          <br />
          <div className="flex align-items-center">
            <RadioButton
              inputId="region2"
              name="region"
              value="District"
              onChange={e => setRegion(e.value)}
              checked={region === 'District'}
            />
            <label htmlFor="region2" className="ml-2">
              District
            </label>
          </div>
        </div>
      </div>
      <br />
      <br />
      {region === 'City' && (
        <div>
          <FloatLabel>
            <InputText id="cityname" value={cityName} onChange={e => setCityName(e.target.value)} />
            <label htmlFor="cityname">City Name</label>
          </FloatLabel>
          <br />
          <FloatLabel>
            <InputText id="description" value={cityDescription} onChange={e => setCityDescription(e.target.value)} />
            <label htmlFor="description">Description </label>
          </FloatLabel>
          <br />
          <Button label="Save City" icon="pi pi-save" style={{ width: 200 }} onClick={saveCity} />
        </div>
      )}
      {region === 'District' && (
        <div>
          <br />
          <FloatLabel>
            <Dropdown
              inputId="dd-city"
              value={selectedCity}
              onChange={e => setSelectedCity(e.value)}
              options={district}
              optionLabel="name"
              className="w-full"
              style={{ width: 270 }}
            />
            <label htmlFor="dd-city">Select a District</label>
          </FloatLabel>
          <br />
          <FloatLabel>
            <InputText id="districtname" value={districtName} onChange={e => setDistrictName(e.target.value)} />
            <label htmlFor="districtname">District Name</label>
          </FloatLabel>
          <br />
          <FloatLabel>
            <InputText id="districtdescription" value={districtDescription} onChange={e => setDistrictDescription(e.target.value)} />
            <label htmlFor="districtdescription">Description </label>
          </FloatLabel>
          <br />
          <Button label="Save City" icon="pi pi-save" style={{ width: 200 }} onClick={saveDistrict} />
          <br />
          <br />
          <DataTable
            value={data}
            paginator
            showGridlines
            rows={25}
            rowsPerPageOptions={[5, 10, 25, 50]}
            tableStyle={{ minWidth: '50rem' }}
            paginatorTemplate="FirstPageLink PrevPageLink CurrentPageReport NextPageLink LastPageLink RowsPerPageDropdown"
            currentPageReportTemplate="{first} to {last} of {totalRecords}"
            paginatorClassName=""
            paginatorLeft={paginatorLeft}
            paginatorRight={paginatorRight}
            //loading={loading}
            dataKey="id"
            filters={filters}
            globalFilterFields={['name', 'country.name', 'representative.name', 'balance', 'status']}
            header={header}
            style={{ fontSize: '12px' }}
            emptyMessage="No customers found."
          >
            <Column header="Sıra" body={(data, options) => options.rowIndex + 1} style={{ fontSize: '12px', textAlign: 'center' }} />
            <Column field="name" header="City" style={{ minWidth: '12rem' }} />
            <Column field="description" header="District" style={{ minWidth: '12rem' }} />
            <Column field="code" header="Code" style={{ minWidth: '12rem' }} />
            <Column header="process" body={actionBodyTemplate}/>
          </DataTable>
        </div>
      )}
    </div>
  );
};
