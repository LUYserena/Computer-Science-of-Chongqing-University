`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2021/11/20 20:21:30
// Design Name: 
// Module Name: dual_synchronous_RAM
// Project Name: 
// Target Devices: 
// Tool Versions: 
// Description: 
// 
// Dependencies: 
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
//////////////////////////////////////////////////////////////////////////////////


module dual_synchronous_RAM(clk,we1,oe1,addra,din_a,dout_a,we2,oe2,addrb,din_b,dout_b);
    parameter DATA_WIDTH = 2;
    parameter ADDR_WIDTH = 4;
    parameter ADDR_DEPTH = 1 << ADDR_WIDTH;
    
    input clk;
    input we1,oe1;
    input[ADDR_WIDTH-1:0] addra;
    input[DATA_WIDTH-1:0] din_a;
    output reg[DATA_WIDTH-1:0] dout_a;
    
    input we2,oe2;
    input[ADDR_WIDTH-1:0] addrb;
    input[DATA_WIDTH-1:0] din_b;
    output reg[DATA_WIDTH-1:0] dout_b;
    
    reg[DATA_WIDTH-1:0] memo[0:ADDR_DEPTH-1];
     
    
    integer i;
    initial
        begin
            for(i=0;i<ADDR_DEPTH;i=i+1)
                memo[i] = 8'h00;
          end
          
      always@(posedge clk)
        begin
            if(we1)
                memo[addra] <= din_a;
         end
         
     always@(posedge clk)
        begin
            if(we2)
            begin
                if(addra != addrb)
                    memo[addrb] <= din_b;
            end
        end
         
     always@(posedge clk)
        begin
            if(oe1 && !we1)
                dout_a <= memo[addra];
             else
                dout_a <= 0;
         end
       
          always@(posedge clk)
            begin
                if(!we2 && oe2)
                    dout_b <= memo[addrb];
                 else
                    dout_b <= 0;
              end
                 
endmodule
