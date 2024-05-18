import React, { useState, useEffect, useRef } from 'react';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import { Toast } from 'primereact/toast';
import { RadioButton } from 'primereact/radiobutton';
import { Dropdown } from 'primereact/dropdown';
import { FloatLabel } from 'primereact/floatlabel';
import DistrictService from '../services/DistrictService';
import '../styles/District.css';

export const District = () => {
  const [loading, setLoading] = useState(false); // Loading durumu için state tanımı
  const [districts, setDistricts] = useState([]);
  const [selectedCity, setSelectedCity] = useState(null);
  const [filters, setFilters] = useState({ global: { value: null, matchMode: 'contains' } });
  const [globalFilterValue, setGlobalFilterValue] = useState('');
  const [districtOptions, setDistrictOptions] = useState([]);
  const [cityName, setCityName] = useState('');
  const [cityDescription, setCityDescription] = useState('');
  const [districtName, setDistrictName] = useState('');
  const [districtDescription, setDistrictDescription] = useState('');
  const [region, setRegion] = useState('');
  const toast = useRef(null);

  useEffect(() => {
    if (region === 'District') {
      setLoading(true); // Yalnızca "District" seçildiğinde loading durumunu true yap

      DistrictService.getAllDistricts()
        .then(response => {
          setDistricts(response.data);
          setLoading(false); // Veriler yüklendikten sonra loading durumunu false yap
        })
        .catch(error => {
          console.log(error);
          toast.current.show({ severity: 'error', summary: 'Error', detail: 'Failed to Load Districts', life: 3000 });
          setLoading(false); // Hata durumunda da loading durumunu false yap
        });

      DistrictService.getDistrictByCode(-1)
        .then(response => {
          setDistrictOptions(response.data);
          console.log('Data : ', response?.data);
        })
        .catch(error => {
          console.log(error);
          toast.current.show({ severity: 'error', summary: 'Error', detail: 'Failed to Load District Options', life: 3000 });
        });
    }
  }, [region]);

  const renderHeader = () => {
    return (
      <div className="header-container">
        <InputText value={globalFilterValue} onChange={onGlobalFilterChange} placeholder="Keyword Search" className="keyword-search" />
        <Button label="Reset" icon="pi pi-refresh" className="reset-button" iconPos="right" onClick={resetFilter} />
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

  const resetFilter = () => {
    setFilters({ global: { value: null, matchMode: 'contains' } });
    setGlobalFilterValue('');
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

    DistrictService.saveCity(values)
      .then(response => {
        console.log('Yeni City Veri: ', response.data);
        toast.current.show({ severity: 'success', summary: 'Successful', detail: 'City Created', life: 3000 });
      })
      .catch(error => {
        console.log(error);
        toast.current.show({ severity: 'error', summary: 'Error', detail: 'Failed to Create City', life: 3000 });
      });
    setCityName('');
    setCityDescription('');
  };

  const saveDistrict = () => {
    const values = {
      name: districtName,
      description: districtDescription,
      code: selectedCity?.id,
    };

    DistrictService.saveDistrict(values)
      .then(response => {
        console.log('Yeni District Veri: ', response.data);
        toast.current.show({ severity: 'success', summary: 'Successful', detail: 'District Created', life: 3000 });
        setDistricts(prevDistricts => [...prevDistricts, response.data]);
      })
      .catch(error => {
        console.log(error);
        toast.current.show({ severity: 'error', summary: 'Error', detail: 'Failed to Create District', life: 3000 });
      });
    setDistrictName('');
    setDistrictDescription('');
    setSelectedCity(null);
  };

  const deleteDistrict = id => {
    DistrictService.deleteDistrict(id)
      .then(() => {
        setDistricts(districts.filter(district => district.id !== id));
        toast.current.show({ severity: 'success', summary: 'Successful', detail: 'District Deleted', life: 3000 });
      })
      .catch(error => {
        console.log(error);
        toast.current.show({ severity: 'error', summary: 'Error', detail: 'Failed to Delete District', life: 3000 });
      });
  };

  const actionBodyTemplate = rowData => {
    return (
      <Button
        type="button"
        icon="pi pi-trash"
        size="small"
        severity="danger"
        outlined
        aria-label="Cancel"
        rounded
        tooltip="Customer tarafından kullanılmayan satırlar silinebilecektir."
        tooltipOptions={{ position: 'left' }}
        onClick={() => deleteDistrict(rowData.id)}
      />
    );
  };

  return (
    <div>
      <Toast ref={toast} />
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
      {loading ? ( // Eğer yükleme durumu true ise loading göster
        <div>Loading...</div>
      ) : (
        <>
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
                  options={districtOptions}
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
              <Button label="Save District" icon="pi pi-save" style={{ width: 200 }} onClick={saveDistrict} />
              <br />
              <br />
              <DataTable
                value={districts}
                paginator
                showGridlines
                rows={25}
                rowsPerPageOptions={[5, 10, 25, 50]}
                tableStyle={{ minWidth: '50rem' }}
                paginatorTemplate="FirstPageLink PrevPageLink CurrentPageReport NextPageLink LastPageLink RowsPerPageDropdown"
                currentPageReportTemplate="{first} to {last} of {totalRecords}"
                paginatorLeft={paginatorLeft}
                paginatorRight={paginatorRight}
                dataKey="id"
                filters={filters}
                globalFilterFields={['name', 'description', 'code']}
                header={header}
                style={{ fontSize: '12px' }}
                emptyMessage="No districts found."
              >
                <Column header="Sıra" body={(data, options) => options.rowIndex + 1} style={{ fontSize: '12px', textAlign: 'center' }} />
                <Column field="name" header="City" style={{ minWidth: '12rem' }} />
                <Column field="description" header="District" style={{ minWidth: '12rem' }} />
                <Column field="code" header="Code" style={{ minWidth: '12rem' }} />
                <Column header="process" body={actionBodyTemplate} />
              </DataTable>
            </div>
          )}
        </>
      )}
    </div>
  );
};
