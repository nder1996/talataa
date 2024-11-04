export interface ApiResponse<T> {
    data: { [key: string]: any };
    meta: Meta;
    error?: ErrorDetails;
  }
  
  export interface Meta {
    message: string;
    status: number;
  }
  
  export interface ErrorDetails {
    code: string;
    details: string;
  }