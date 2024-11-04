import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {

  constructor() { }

  /**
    * Guarda un objeto en localStorage
    * @param key Clave para almacenar el objeto
    * @param value Objeto a almacenar
    */
  save(key: string, value: any): void {
    try {
      const serializedValue = JSON.stringify(value);
      localStorage.setItem(key, serializedValue);
    } catch (error) {
      console.error('Error al guardar en localStorage:', error);
    }
  }

  /**
   * Obtiene un objeto del localStorage
   * @param key Clave del objeto a obtener
   * @returns El objeto almacenado o null si no existe
   */
  get(key: string): any {
    try {
      const item = localStorage.getItem(key);
      return item ? JSON.parse(item) : null;
    } catch (error) {
      console.error('Error al obtener de localStorage:', error);
      return null;
    }
  }

  /**
   * Obtiene todos los objetos almacenados en localStorage
   * @returns Un objeto con todas las claves y valores
   */
  getAll(): { [key: string]: any } {
    const items: { [key: string]: any } = {};
    try {
      for (let i = 0; i < localStorage.length; i++) {
        const key = localStorage.key(i);
        if (key) {
          const value = this.get(key);
          items[key] = value;
        }
      }
    } catch (error) {
      console.error('Error al obtener todos los items:', error);
    }
    return items;
  }

  /**
   * Añade un elemento a una lista existente
   * @param key Clave de la lista
   * @param item Elemento a añadir
   * @returns true si se añadió correctamente
   */
  addToList<T>(key: string, item: T): boolean {
    try {
      const list = this.getList<T>(key) || [];
      list.push(item);
      this.save(key, list);
      return true;
    } catch (error) {
      console.error('Error al añadir a la lista:', error);
      return false;
    }
  }

  /**
   * Obtiene una lista del localStorage
   * @param key Clave de la lista
   * @returns Array almacenado o array vacío si no existe
   */
  getList<T>(key: string): T[] {
    try {
      const list = this.get(key);
      return Array.isArray(list) ? list : [];
    } catch (error) {
      console.error('Error al obtener la lista:', error);
      return [];
    }
  }

  /**
   * Actualiza un elemento específico en una lista
   * @param key Clave de la lista
   * @param index Índice del elemento a actualizar
   * @param newItem Nuevo valor del elemento
   * @returns true si se actualizó correctamente
   */
  updateListItem<T>(key: string, index: number, newItem: T): boolean {
    try {
      const list = this.getList<T>(key);
      if (index >= 0 && index < list.length) {
        list[index] = newItem;
        this.save(key, list);
        return true;
      }
      return false;
    } catch (error) {
      console.error('Error al actualizar elemento de la lista:', error);
      return false;
    }
  }

  /**
   * Elimina un elemento de una lista
   * @param key Clave de la lista
   * @param index Índice del elemento a eliminar
   * @returns true si se eliminó correctamente
   */
  removeFromList<T>(key: string, index: number): boolean {
    try {
      const list = this.getList<T>(key);
      if (index >= 0 && index < list.length) {
        list.splice(index, 1);
        this.save(key, list);
        return true;
      }
      return false;
    } catch (error) {
      console.error('Error al eliminar de la lista:', error);
      return false;
    }
  }

  /**
   * Filtra elementos de una lista según un criterio
   * @param key Clave de la lista
   * @param filterFn Función de filtrado
   * @returns Array con los elementos que cumplen el criterio
   */
  filterList<T>(key: string, filterFn: (item: T) => boolean): T[] {
    try {
      const list = this.getList<T>(key);
      return list.filter(filterFn);
    } catch (error) {
      console.error('Error al filtrar la lista:', error);
      return [];
    }
  }

  /**
   * Elimina un objeto del localStorage
   * @param key Clave del objeto a eliminar
   */
  remove(key: string): void {
    try {
      localStorage.removeItem(key);
    } catch (error) {
      console.error('Error al eliminar:', error);
    }
  }

  /**
   * Limpia todo el localStorage
   */
  clear(): void {
    try {
      localStorage.clear();
    } catch (error) {
      console.error('Error al limpiar localStorage:', error);
    }
  }


  /**
   * Guarda una lista en localStorage
   * @param key Clave de la lista
   * @param list Lista a guardar
   */
  saveList<T>(key: string, list: T[]): void {
    try {
      const serializedList = JSON.stringify(list);
      localStorage.setItem(key, serializedList);
    } catch (error) {
      console.error('Error al guardar la lista:', error);
    }
  }


   /**
   * Actualiza una lista completa
   * @param key Clave de la lista
   * @param newList Nueva lista que reemplazará la anterior
   * @returns true si se actualizó correctamente
   */
   updateCompleteList<T>(key: string, newList: T[]): boolean {
    try {
      this.saveList(key, newList);
      return true;
    } catch (error) {
      console.error('Error al actualizar la lista completa:', error);
      return false;
    }
  }
  
}
