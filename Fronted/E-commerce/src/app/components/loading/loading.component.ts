import { Component } from '@angular/core';
import { LoadingService } from 'src/service/loading.service';

@Component({
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.css']
})
export class LoadingComponent {
  constructor(private loadingService: LoadingService){}

  public isSpinnerVisible:boolean = false;
  loading$ = this.loadingService.loading$;

  ngOnInit(): void {
    this.loadingService.getSpinnerState().subscribe((state: boolean) => {
      this.isSpinnerVisible = state;
    });
  }

}
